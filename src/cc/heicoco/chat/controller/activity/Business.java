package cc.heicoco.chat.controller.activity;

import java.util.LinkedList;

import cc.heicoco.chat.model.bo.FriendsDealBo;
import cc.heicoco.chat.model.bo.LogRegBo;
import cc.heicoco.chat.model.bo.impl.FriendsDealBoImpl;
import cc.heicoco.chat.model.bo.impl.LogRegBoImpl;
import cc.heicoco.chat.model.entity.User;
import cc.heicoco.chat.model.pojo.Friend;

/**
 * <br>Title:TODO 事务控制器
 * <br>Description:TODO 处理除聊天事务以外的工作，现包括登录注册控制，添加好友控制
 * <br>Author:董嘉文(roco2015@163.com)
 * <br>Date:2016年5月26日
 */
public class Business {
    
    private LogRegBo lr = null;
    private FriendsDealBo fd = null;
    
    public Business(){
    }
    
    public String reg(User user){
        if(lr == null){
            lr = new LogRegBoImpl();
        }
        return lr.reg(user);
    }
    
    public String log(User user){
        if(lr == null){
            lr = new LogRegBoImpl();
        }
        return lr.log(user);
    }
        
    public int addFriend(LinkedList<Friend> friendList, String cUserName, String userName){
        if(fd == null){
            fd = new FriendsDealBoImpl();
        }
        return fd.addFriend(friendList, cUserName, userName);
    }
    
    public String[] queryFriend(String account){
        if(fd == null){
            fd = new FriendsDealBoImpl();
        }
        return fd.queryFriend(account);
    }
    
    public void getFriend(LinkedList<Friend> friendList, String account){
        if(fd == null){
            fd = new FriendsDealBoImpl();
        }
        fd.getFriend(friendList, account);
    }
    
    public boolean deleteFriend(LinkedList<Friend> friendList, String account, String userName){
        if(fd == null){
            fd = new FriendsDealBoImpl();
        }
        return fd.deleteFriend(friendList, account, userName);
    }
    
    public boolean connFriend(String userName){
        if(fd == null){
            fd = new FriendsDealBoImpl();
        }
        return fd.connFriend(userName);
    }
}
