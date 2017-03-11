package cc.heicoco.chat.model.bo.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.LinkedList;

import android.util.Log;
import cc.heicoco.chat.model.bo.ChatBo;
import cc.heicoco.chat.model.dao.TransferDao;
import cc.heicoco.chat.model.dao.impl.TransferDaoImpl;
import cc.heicoco.chat.model.pojo.Friend;
import cc.heicoco.chat.model.utils.MyGlobal;

public class ChatBoImpl implements ChatBo {

    TransferDao dao = new TransferDaoImpl();

    DatagramPacket dpSend;
    DatagramPacket dpReceive;


    public ChatBoImpl() {
        super();
        dpSend = new DatagramPacket("".getBytes(), "".length());
        dpReceive = new DatagramPacket("".getBytes(), "".length());
    }

    /**
     * <br>Description:TODO service调用此来发送消息，准备好dp，ds
     * <br>Author:董嘉文(roco2015@163.com)
     * <br>Date:2016年5月8日
     * @see cc.heicoco.chat.model.bo.ChatBo#send(java.lang.String)
     */
    @Override
    public void send(String msg, String currentUser) {
        // TODO Auto-generated method stub
        try {
            msg = currentUser + "," + msg;
            dao.send(dpSend, MyGlobal.getDs(), MyGlobal.getAimAddress(), msg.getBytes(), MyGlobal.getPortAim());
            Log.d("TransferBoImpl", "send方法正常结束");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("TransferBoImpl", "send方法出错");
        }
    }

    /**
     * <br>Description:TODO service调用此接收消息，准备好dp（不通用），ds（通用）
     * <br>Author:董嘉文(roco2015@163.com)
     * <br>Date:2016年5月8日
     * @see cc.heicoco.chat.model.bo.ChatBo#receive()
     */
    @Override
    public void receive(LinkedList<Friend> friendList) {
        // TODO Auto-generated method stub
        dpReceive.setData(MyGlobal.getByt());
        dpReceive.setLength(MyGlobal.getByt().length);
        try {
            while(MyGlobal.getDs() == null){
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            dao.receive(dpReceive, MyGlobal.getDs());
            dao.dealMsg(friendList);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("TransferBoImpl", "receive方法出错");
        }
    }

    /**
     * <br>Description:TODO 服务关闭时调用，清理udp套接字
     * <br>Author:董嘉文(roco2015@163.com)
     * <br>Date:2016年5月8日
     * @see cc.heicoco.chat.model.bo.ChatBo#close()
     */
    @Override
    public void close() {
        // TODO Auto-generated method stub
        dao.close();
    }

}
