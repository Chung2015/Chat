package cc.heicoco.chat.model.dao;

import java.io.PrintWriter;
import java.util.LinkedList;

import cc.heicoco.chat.model.entity.User;
import cc.heicoco.chat.model.pojo.Friend;

public interface ConServerTcpDao {
    
    public String reg(User user,PrintWriter out) throws InterruptedException;
    public String log(User user,PrintWriter out) throws InterruptedException;
    public void getFriend(LinkedList<Friend> friendList, String account,PrintWriter out) throws InterruptedException;
    public String[] queryFriend(String userName,PrintWriter out) throws InterruptedException;
    public int addFriend(LinkedList<Friend> friendList, String cUserName, String userName,PrintWriter out) throws InterruptedException;
    public boolean deleteFriend(LinkedList<Friend> friendList, String cUserName, String userName,PrintWriter out) throws InterruptedException;
    public boolean connFriend(String userName,PrintWriter out) throws InterruptedException;
}
