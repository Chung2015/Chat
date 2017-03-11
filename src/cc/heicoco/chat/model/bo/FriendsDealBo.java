package cc.heicoco.chat.model.bo;

import java.util.LinkedList;

import cc.heicoco.chat.model.pojo.Friend;

public interface FriendsDealBo {
    public int addFriend(LinkedList<Friend> friendList, String cUserName, String userName);
    public String[] queryFriend(String userName);
    public void getFriend(LinkedList<Friend> friendList, String account);
    public boolean deleteFriend(LinkedList<Friend> friendList, String account, String userName);
    public boolean connFriend(String userName);
}
