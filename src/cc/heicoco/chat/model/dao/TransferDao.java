package cc.heicoco.chat.model.dao;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;

import cc.heicoco.chat.model.pojo.Friend;

public interface TransferDao {
    public void send(DatagramPacket dp,DatagramSocket ds,InetAddress ipAddr,byte[] bytes,int port) throws IOException ;
    public void receive(DatagramPacket dp,DatagramSocket ds) throws IOException ;
    public void dealMsg(LinkedList<Friend> friendList);
    public void close();
}
