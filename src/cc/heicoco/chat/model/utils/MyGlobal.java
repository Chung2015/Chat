package cc.heicoco.chat.model.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import cc.heicoco.chat.model.entity.User;
import cc.heicoco.chat.model.pojo.Friend;

public class MyGlobal {

	private static InetAddress serverAddress;
	private static InetAddress clientAddress;
	private static InetAddress aimAddress;
	private static int portAim;
	private static Integer portLocal = -1;
	private final static int PORTSERVER = 40628;
    private final static int PORTCHAT = 21234;

    private static DatagramSocket ds = null;
    
	private static byte[] byt = new byte[1024];
	private static Socket socketLR;
	private static InputStream is;
    private static Scanner in;
    private static OutputStream os;
    private static PrintWriter out;
    
    private static LinkedList<Friend> friendList;
    private static Queue<String> serverMsgQueue;
    private static User currentUser;

    private static boolean RECING = true;
    private static boolean recport = false;
    private static boolean hearting = true;
    
    private final static String OPSUCCEED ="300";
    private final static String OPFAIL_ACCOUNTSAMED ="301";
    private final static String OPFAIL_NOTHEUSER ="302";
    private final static String OPFAIL_WRONGPASSWORD="303";
    private final static String OPFAIL_ADDFRI="304";
    private final static String OPFAIL_DELFRI="305";
    private final static String OPFAIL_CONNFRI="306";
    private final static String OPFAIL_UNKNOWN ="310";
    
    private final static String THEEND = "409";
    
    private final static String REQ_REG ="501";
    private final static String REQ_LOG ="502";
    private final static String REQ_QCUSER ="503";
    private final static String REQ_GETFRI ="504";
    private final static String REQ_QFRIEND ="505";
    private final static String REQ_ADDFRIEND ="506";
    private final static String REQ_DELFRIEND ="507";
    private final static String REQ_CONNFRIEND ="508";
    private final static String REQ_CLOSETCP ="509";   
    private final static String REQ_CONNFRIENDREACTIVE ="510"; 

	static {
	    
	    try {
            serverAddress = InetAddress.getByName("104.224.160.148");
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
    public static InetAddress getServerAddress() {
        return serverAddress;
    }
    /**
     * <br>Description:TODO 服务为服务器地址初始化
     * <br>Author:董嘉文(roco2015@163.com)
     * <br>Date:2016年5月9日
     * @param serverAddress
     */
    public static void setServerAddress(InetAddress serverAddress) {
        MyGlobal.serverAddress = serverAddress;
    }
    public static InetAddress getClientAddress() {
        return clientAddress;
    }
    public static void setClientAddress(InetAddress clientAddress) {
        MyGlobal.clientAddress = clientAddress;
    }
    public static InetAddress getAimAddress() {
        return aimAddress;
    }
    public static void setAimAddress(InetAddress aimAddress) {
        MyGlobal.aimAddress = aimAddress;
    }
    
    public static int getPortAim() {
        return portAim;
    }
    public static void setPortAim(int portAim) {
        MyGlobal.portAim = portAim;
    }
    
    public static Integer getPortLocal() {
        return portLocal;
    }
    public static void setPortLocal(Integer portLocal) {
        MyGlobal.portLocal = portLocal;
    }
    
    public static int getPortchat() {
        return PORTCHAT;
    }
    public static String getReqQcuser() {
        return REQ_QCUSER;
    }
    
    public static String getReqClosetcp() {
        return REQ_CLOSETCP;
    }

    public static DatagramSocket getDs() {
        return ds;
    }
    public static void setDs(DatagramSocket ds) {
        MyGlobal.ds = ds;
    }
    public static byte[] getByt() {
        return byt;
    }
    
/*    public static Socket getSocketLR() {
        if(socketLR == null){
            try {
                socketLR = new Socket(serverAddress, PORTSERVER);
                is = socketLR.getInputStream();
                in = new Scanner(is);
                os = socketLR.getOutputStream();
                out = new PrintWriter(os,true);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return socketLR;
    }*/
    
    public static Scanner getIn() {
        if(in == null){
            try {
                socketLR = new Socket(serverAddress, PORTSERVER);
                is = socketLR.getInputStream();
                in = new Scanner(is);
                os = socketLR.getOutputStream();
                out = new PrintWriter(os,true);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return in;
    }
    public static PrintWriter getOut() {
        if(out == null){
            try {
                socketLR = new Socket(serverAddress, PORTSERVER);
                is = socketLR.getInputStream();
                in = new Scanner(is);
                os = socketLR.getOutputStream();
                out = new PrintWriter(os,true);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return out;
    }
    
    public static LinkedList<Friend> getFriendList() {
        return friendList;
    }
    
    public static void setFriendList(LinkedList<Friend> friendList) {
        MyGlobal.friendList = friendList;
    }
    public static Queue<String> getServerMsgQueue() {
        if(serverMsgQueue == null){
            serverMsgQueue = new LinkedList<String>();
        }
        return serverMsgQueue;
    }
    public static void setServerMsgQueue(Queue<String> serverMsgQueue) {
        MyGlobal.serverMsgQueue = serverMsgQueue;
    }
    
    public static User getCurrentUser() {
        return currentUser;
    }
    public static void setCurrentUser(User currentUser) {
        MyGlobal.currentUser = currentUser;
    }
    
    public static boolean isRECING() {
        return RECING;
    }
    public static void setRECING(boolean rECING) {
        RECING = rECING;
    }
    
    public static boolean isRecport() {
        return recport;
    }
    public static void setRecport(boolean recport) {
        MyGlobal.recport = recport;
    }
    
    public static boolean isHearting() {
        return hearting;
    }
    public static void setHearting(boolean hearting) {
        MyGlobal.hearting = hearting;
    }
    public static String getOpsucceed() {
        return OPSUCCEED;
    }
    public static String getOpfailAccountsamed() {
        return OPFAIL_ACCOUNTSAMED;
    }
    public static String getOpfailNotheuser() {
        return OPFAIL_NOTHEUSER;
    }
    public static String getOpfailWrongpassword() {
        return OPFAIL_WRONGPASSWORD;
    }
    public static String getOpfailUnknown() {
        return OPFAIL_UNKNOWN;
    }
    public static String getOpfailAddfri() {
        return OPFAIL_ADDFRI;
    }
    public static String getOpfailConnfri() {
        return OPFAIL_CONNFRI;
    }
    public static String getTheend() {
        return THEEND;
    }
    public static String getReqReg() {
        return REQ_REG;
    }
    public static String getReqLog() {
        return REQ_LOG;
    }
    public static String getReqGetfri() {
        return REQ_GETFRI;
    }
    
    public static String getReqQfriend() {
        return REQ_QFRIEND;
    }
    
    public static String getReqAddfriend() {
        return REQ_ADDFRIEND;
    }
    
    public static String getReqDelfriend() {
        return REQ_DELFRIEND;
    }
    
    public static String getOpfailDelfri() {
        return OPFAIL_DELFRI;
    }
    
    public static String getReqConnfriend() {
        return REQ_CONNFRIEND;
    }
    
    public static String getReqConnfriendreactive() {
        return REQ_CONNFRIENDREACTIVE;
    }
    /**
     * <br>Description:TODO 手动清理套接字，考虑用这个方法替换掉下面的gc方法
     * <br>Author:董嘉文(roco2015@163.com)
     * <br>Date:2016年5月27日
     * @throws IOException
     */
    public static void clean() throws IOException{

        if(out != null){
            out.println(REQ_CLOSETCP);
            out.close();
            out = null;
        }
        if(os != null){
            os.close();
            os = null;
        }
        if(in != null){
            in.close();
            in = null;
        }
        if(is != null){
            is.close();
            is = null;
        }
        if(socketLR != null){
            socketLR.close();
            socketLR = null;
        }
    }
    /**
     * <br>Description:TODO gc时清理套接字
     * <br>Author:董嘉文(roco2015@163.com)
     * <br>Date:2016年5月9日
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();

        if(out != null){
            out.println("19931009");
            out.close();
            out = null;
        }
        if(os != null){
            os.close();
            os = null;
        }
        if(in != null){
            in.close();
            in = null;
        }
        if(is != null){
            is.close();
            is = null;
        }
        if(socketLR != null){
            socketLR.close();
            socketLR = null;
        }
    }
    
	
}
