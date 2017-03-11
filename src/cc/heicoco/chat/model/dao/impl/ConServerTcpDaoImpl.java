package cc.heicoco.chat.model.dao.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import android.util.Log;
import cc.heicoco.chat.model.dao.ConServerTcpDao;
import cc.heicoco.chat.model.dao.UdpHolePunchingDao;
import cc.heicoco.chat.model.entity.User;
import cc.heicoco.chat.model.pojo.Friend;
import cc.heicoco.chat.model.utils.MyGlobal;

public class ConServerTcpDaoImpl implements ConServerTcpDao {

    private Queue<String> serverMsgQueue = MyGlobal.getServerMsgQueue();

    /**
     * <br>Description:TODO 以下方法只负责向服务器发送数据，之后从消息队列中取响应值，并返还响应代码给Bo
     * <br>Author:董嘉文(roco2015@163.com)
     * <br>Date:2016年5月10日
     * @throws InterruptedException 
     * @see cc.heicoco.chat.model.dao.ConServerTcpDao#reg(java.net.Socket, cc.heicoco.chat.model.entity.User)
     */
    @Override
    public String reg(User user, PrintWriter out) throws InterruptedException {
        // TODO Auto-generated method stub

        out.println(MyGlobal.getReqReg());
        out.println(user.getAccount());
        out.println(user.getUserName());
        out.println(user.getPassword());
        String responCode = null;
        
        while(MyGlobal.isRECING()){
            Thread.sleep(500);
        }
        MyGlobal.setRECING(true);
        responCode = serverMsgQueue.poll();
        if (responCode.equals(MyGlobal.getOpsucceed())) {
            responCode = serverMsgQueue.poll();
            if (responCode.equals(MyGlobal.getOpsucceed())) {
                responCode = serverMsgQueue.poll();
                user.setIp(serverMsgQueue.poll());
                MyGlobal.setPortLocal(Integer.valueOf(serverMsgQueue.poll()));
            }
        }

        return responCode;
    }

    @Override
    public String log(User user, PrintWriter out) throws InterruptedException {
        // TODO Auto-generated method stub

        out.println(MyGlobal.getReqLog());
        out.println(user.getAccount());
        out.println(user.getPassword());
        while(MyGlobal.isRECING()){
            Thread.sleep(500);
        }
        MyGlobal.setRECING(true);

        Log.i("登录", "登录接收");
        String responCode = serverMsgQueue.poll();
        if (responCode.equals(MyGlobal.getOpsucceed())) {
            responCode = serverMsgQueue.poll();
            if (responCode.equals(MyGlobal.getOpsucceed())) {
                user.setUserName(serverMsgQueue.poll());
                user.setIp(serverMsgQueue.poll());
                MyGlobal.setPortLocal(Integer.valueOf(serverMsgQueue.poll()));
            }
        }

        return responCode;
    }

    @Override
    public void getFriend(LinkedList<Friend> friendList, String account, PrintWriter out)
            throws InterruptedException {
        // TODO Auto-generated method stub
        out.println(MyGlobal.getReqGetfri());
        out.println(account);

        while(MyGlobal.isRECING()){
            Thread.sleep(500);
        }
        MyGlobal.setRECING(true);
        
        int sum = Integer.valueOf(serverMsgQueue.poll());
        Friend fri;
        while (sum != 0) {
            fri = new Friend();
            fri.setName(serverMsgQueue.poll());
            fri.setIp(serverMsgQueue.poll());
            friendList.add(fri);
            sum--;
        }
    }

    @Override
    public String[] queryFriend(String userName, PrintWriter out) throws InterruptedException {
        // TODO Auto-generated method stub
        out.println(MyGlobal.getReqQfriend());
        out.println(userName);

        while(MyGlobal.isRECING()){
            Thread.sleep(500);
        }
        MyGlobal.setRECING(true);
        
        int sum = Integer.valueOf(serverMsgQueue.poll());
        String[] result = new String[sum];

        while (sum != 0) {
            result[sum - 1] = serverMsgQueue.poll();
            sum--;
        }
        return result;
    }

    /**
     * <br>Description:TODO 添加联系人，需要做两件事，1.将需要添加的传给服务器。2.将服务器返回的具体信息添加到列表里
     * <br>Author:董嘉文(roco2015@163.com)
     * <br>Date:2016年5月29日
     * @throws InterruptedException 
     * @see cc.heicoco.chat.model.dao.ConServerTcpDao#addFriend(java.util.LinkedList, java.lang.String, java.util.Scanner, java.io.PrintWriter)
     */
    @Override
    public int addFriend(LinkedList<Friend> friendList, String cUserName, String userName, PrintWriter out)
            throws InterruptedException {
        // TODO Auto-generated method stub
        boolean hasBeenAdd = true;
        Iterator<Friend> it = friendList.iterator();
        Friend friend;
        while (it.hasNext()) {
            friend = it.next();
            if (friend.getName().equals(userName)) {
                hasBeenAdd = false;
                break;
            }
        }
        if (hasBeenAdd) {
            out.println(MyGlobal.getReqAddfriend());
            out.println(cUserName);
            out.println(userName);

            while(MyGlobal.isRECING()){
                Thread.sleep(500);
            }
            MyGlobal.setRECING(true);
            
            if (serverMsgQueue.poll().equals(MyGlobal.getOpsucceed())) {
                Friend fri = new Friend();
                fri.setName(userName);
                fri.setIp(serverMsgQueue.poll());
                friendList.add(fri);
                return 0;
            } else {
                return 2;
            }
        } else {
            return 1;
        }
    }

    @Override
    public boolean deleteFriend(LinkedList<Friend> friendList, String account, String userName,
            PrintWriter out) throws InterruptedException {
        // TODO Auto-generated method stub
        out.println(MyGlobal.getReqDelfriend());
        out.println(account);
        out.println(userName);

        while(MyGlobal.isRECING()){
            Thread.sleep(500);
        }
        MyGlobal.setRECING(true);
        
        if (serverMsgQueue.poll().equals(MyGlobal.getOpsucceed())) {
            Iterator<Friend> it = friendList.iterator();
            Friend fri;
            while (it.hasNext()) {
                fri = it.next();
                if (fri.getName().equals(userName)) {
                    it.remove();
                    break;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>Description:TODO 主动发起连接，还会有一个被动建立连接的，不要混了
     * <br>Author:董嘉文(roco2015@163.com)
     * <br>Date:2016年5月31日
     * @see cc.heicoco.chat.model.dao.ConServerTcpDao#connFriend(java.lang.String, java.io.PrintWriter)
     */
    @Override
    public boolean connFriend(String userName, PrintWriter out) throws InterruptedException {
        // TODO Auto-generated method stub
        out.println(MyGlobal.getReqConnfriend());
        out.println(userName);
        //TODO 发送心跳包
        
        new Thread(new Runnable() {
            UdpHolePunchingDao uhpDao = new UdpHolePunchingDaoImpl();
            @Override
            public void run() {
                // TODO Auto-generated method stub
                MyGlobal.setHearting(true);
                try {
                    uhpDao.sendHeartBeatPackage(MyGlobal.getCurrentUser().getUserName());
                } catch (IOException | InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
        
        while(MyGlobal.isRECING()){
            Thread.sleep(500);
        }
        MyGlobal.setRECING(true);

        Log.i("connFriend", "MyGlobal.setRECING(true)");
        if (serverMsgQueue.poll().equals(MyGlobal.getOpsucceed())) {
            String ipAddrB = serverMsgQueue.poll();  //B的ip
            int portB = Integer.valueOf(serverMsgQueue.poll());  //B的port
            int portA = Integer.valueOf(serverMsgQueue.poll());  //A的port

            Log.i("connFriend", ipAddrB+":"+portB+":"+portA);
            try {
                MyGlobal.setAimAddress(InetAddress.getByName(ipAddrB));
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            } finally {
                MyGlobal.setPortAim(portB);
            }
            //TODO 发送探测包
            Log.i("ConServerTcpDaoImpl", "准备发送探测包");
            UdpHolePunchingDao uhpDao2 = new UdpHolePunchingDaoImpl();
            try {
                uhpDao2.sendDetectionPackage(MyGlobal.getCurrentUser().getUserName(),ipAddrB, portB, portA);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            MyGlobal.setHearting(false);
            return true;
        } else {
            return false;
        }

    }

}
