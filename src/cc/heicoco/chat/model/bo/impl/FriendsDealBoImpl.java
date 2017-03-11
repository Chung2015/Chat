package cc.heicoco.chat.model.bo.impl;

import java.util.LinkedList;

import cc.heicoco.chat.model.bo.FriendsDealBo;
import cc.heicoco.chat.model.dao.ConServerTcpDao;
import cc.heicoco.chat.model.dao.impl.ConServerTcpDaoImpl;
import cc.heicoco.chat.model.pojo.Friend;
import cc.heicoco.chat.model.utils.MyGlobal;

public class FriendsDealBoImpl implements FriendsDealBo {

    ConServerTcpDao csDao = null;
    
    @Override
    public int addFriend(LinkedList<Friend> friendList, String cUserName, String userName) {
        // TODO Auto-generated method stub
        if(csDao == null){
            csDao = new ConServerTcpDaoImpl();
        }
        int result = -1;
        try {
            result = csDao.addFriend(friendList, cUserName, userName, MyGlobal.getOut());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String[] queryFriend(String userName) {
        // TODO Auto-generated method stub
        if(csDao == null){
            csDao = new ConServerTcpDaoImpl();
        }
        String[] result = null;
        try {
            result = csDao.queryFriend(userName, MyGlobal.getOut());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void getFriend(LinkedList<Friend> friendList, String account) {
        // TODO Auto-generated method stub
        if(csDao == null){
            csDao = new ConServerTcpDaoImpl();
        }
        try {
            csDao.getFriend(friendList, account, MyGlobal.getOut());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteFriend(LinkedList<Friend> friendList, String account, String userName) {
        // TODO Auto-generated method stub
        if(csDao == null){
            csDao = new ConServerTcpDaoImpl();
        }
        boolean result = false;
        try {
            result = csDao.deleteFriend(friendList, account, userName, MyGlobal.getOut());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean connFriend(String userName) {
        // TODO Auto-generated method stub
        if(csDao == null){
            csDao = new ConServerTcpDaoImpl();
        }
        boolean result = false;
        try {
            result = csDao.connFriend(userName, MyGlobal.getOut());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    
}
