package cc.heicoco.chat.model.dao.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;

import android.util.Log;
import cc.heicoco.chat.model.dao.DealRegularDao;
import cc.heicoco.chat.model.dao.TransferDao;
import cc.heicoco.chat.model.pojo.Friend;
import cc.heicoco.chat.model.pojo.Msg;

public class TransferDaoImpl implements TransferDao {
    
    private DatagramPacket dp;
    private DealRegularDao rDao = new DealRegularDaoImpl();
    @Override
    public void send(DatagramPacket dp,DatagramSocket ds,InetAddress ipAddr,byte[] bytes,int port) throws IOException {
        // TODO Auto-generated method stub
        dp.setAddress(ipAddr);
        dp.setData(bytes);
        dp.setLength(bytes.length);
        dp.setPort(port);
        ds.send(dp);
        Log.d("TransferDaoImpl", "发送成功");
    }

    @Override
    public void receive(DatagramPacket dp,DatagramSocket ds) throws IOException {
        // TODO Auto-generated method stub
        Log.d("TransferDaoImpl", "准备接收消息");
        ds.receive(dp);
        Log.d("TransferDaoImpl", "接收消息成功");
        this.dp = dp;
    }

    @Override
    public void dealMsg(LinkedList<Friend> friendList) {
        // TODO Auto-generated method stub
        Log.d("TransferDaoImpl", "处理UDP消息");
        String from_ip = dp.getAddress().getHostAddress();
        String info = new String(dp.getData(), dp.getOffset(), dp.getLength());
        
        String[] result = rDao.udpPackage(info);
        if(result.length == 2){
            Friend fri = null;
            while(friendList.iterator().hasNext()){
                fri = friendList.iterator().next();
                if(fri.getName().equals(result[0])){
                    break;
                }
            }
            if(fri == null){
                fri = new Friend();
                fri.setName(result[0]);
                fri.setIp(from_ip);
                fri.getMsgList().add(new Msg(result[1], Msg.TYPE_RECEIVED));
                friendList.add(fri);
            }else{
                fri.getMsgList().add(new Msg(result[1], Msg.TYPE_RECEIVED));
            }
        }
        
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }


}
