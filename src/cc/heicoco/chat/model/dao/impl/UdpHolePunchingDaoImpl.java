package cc.heicoco.chat.model.dao.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import cc.heicoco.chat.model.dao.UdpHolePunchingDao;
import cc.heicoco.chat.model.utils.MyGlobal;

public class UdpHolePunchingDaoImpl implements UdpHolePunchingDao {

    private DatagramPacket dp = new DatagramPacket("".getBytes(), "".length());
    @Override
    public boolean sendHeartBeatPackage(String userName) throws IOException, InterruptedException{
        // TODO Auto-generated method stub
        DatagramSocket ds = new DatagramSocket();
        
        dp.setAddress(MyGlobal.getServerAddress());
        dp.setData(userName.getBytes());
        dp.setLength(userName.getBytes().length);
        dp.setPort(MyGlobal.getPortchat());
        MyGlobal.setDs(ds);
        while(MyGlobal.isHearting()){
            ds.send(dp);
            Thread.sleep(200);
        }
        MyGlobal.setHearting(true);
        return true;
    }
    
    @Override
    public boolean sendDetectionPackage(String userName, String ipAddrB, int portB, int portA) throws IOException, InterruptedException {
        // TODO Auto-generated method stub
        DatagramSocket ds = MyGlobal.getDs();
        dp.setAddress(InetAddress.getByName(ipAddrB));
        dp.setData(userName.getBytes());
        dp.setLength(userName.getBytes().length);
        dp.setPort(portB);
        ds.send(dp);
        ds.send(dp);
        ds.send(dp);
        ds.send(dp);
        ds.send(dp);
        
        MyGlobal.setAimAddress(InetAddress.getByName(ipAddrB));
        MyGlobal.setPortAim(portB);
        
        MyGlobal.setHearting(true);
        return true;
    }

}
