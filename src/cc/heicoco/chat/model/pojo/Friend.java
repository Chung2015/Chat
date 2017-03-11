package cc.heicoco.chat.model.pojo;

import java.util.ArrayList;

public class Friend {
    
    /**
     * <br>Description:TODO 变量描述
     * <br>Author:董嘉文(roco2015@163.com)
     * <br>Date:2016年5月29日
     */
    private String userName;
	private String ip;
	private ArrayList<Msg> msgList = new ArrayList<Msg>();
	
    public String getName() {
		return userName;
	}
	public void setName(String userName) {
		this.userName = userName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
    public ArrayList<Msg> getMsgList() {
        return msgList;
    }
}
