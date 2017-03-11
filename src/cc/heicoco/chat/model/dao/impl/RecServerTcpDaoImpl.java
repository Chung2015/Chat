package cc.heicoco.chat.model.dao.impl;

import java.io.IOException;
import java.util.Queue;
import java.util.Scanner;

import android.util.Log;
import cc.heicoco.chat.model.dao.DealRegularDao;
import cc.heicoco.chat.model.dao.RecServerTcpDao;
import cc.heicoco.chat.model.dao.UdpHolePunchingDao;
import cc.heicoco.chat.model.utils.MyGlobal;

public class RecServerTcpDaoImpl implements RecServerTcpDao {

    private DealRegularDao drDao = new DealRegularDaoImpl();

    private UdpHolePunchingDao uhpDao = new UdpHolePunchingDaoImpl();

    private String temp = null;

    /**
     * <br>Description:TODO 本方法只负责接收服务器发来的数据，判断并存入消息队列中
     * <br>Author:董嘉文(roco2015@163.com)
     * <br>Date:2016年5月31日
     * @see cc.heicoco.chat.model.dao.RecServerTcpDao#tcpReceive(java.util.Queue, java.util.Scanner)
     */
    @Override
    public void tcpReceive(Queue<String> serverMsgQueue, Scanner in) {
        // TODO Auto-generated method stub
        while (true) {
            temp = in.nextLine();
            Log.i("RecServerTcpDaoImpl", "服务器已返回数据:"+temp);
            if (temp.startsWith(MyGlobal.getTheend())) {
                MyGlobal.setRECING(false);
            } else if (temp.startsWith(MyGlobal.getReqConnfriendreactive())) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO 正则表达式处理接收的消息，消息包括请求人的 请求码，ipAddrB，portB,portA
                        String[] ans = drDao.udpholeReqInfo(temp);
                        if(ans[3].equals("")){
                            try {
                                // TODO 发送心跳包
                                uhpDao.sendHeartBeatPackage(MyGlobal.getCurrentUser().getUserName());
                            } catch (IOException | InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }else{
                            try {
                                // TODO 发送探测包
                                uhpDao.sendDetectionPackage(MyGlobal.getCurrentUser().getUserName(), ans[1], Integer.valueOf(ans[2]), Integer.valueOf(ans[3]));
                            } catch (NumberFormatException | IOException | InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            } else {
                serverMsgQueue.offer(temp);
            }
        }

    }

}
