package cc.heicoco.chat.model.bo;

import java.util.LinkedList;

import cc.heicoco.chat.model.pojo.Friend;

public interface ChatBo {
	public void send(String msg, String currentUser);
	public void receive(LinkedList<Friend> friendList);
	public void close();
}
