package cc.heicoco.chat.controller.service;

import java.util.LinkedList;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import cc.heicoco.chat.model.bo.ChatBo;
import cc.heicoco.chat.model.bo.impl.ChatBoImpl;
import cc.heicoco.chat.model.pojo.Friend;
import cc.heicoco.chat.model.utils.MyGlobal;

public class ChatService extends Service {
    
    private ChatBo chatBo = null;
    
    private ChatServicBinder csBinder = new ChatServicBinder();
    
    public class ChatServicBinder extends Binder{
        /**
         * <br>Description:TODO 调用bo方法发送消息
         * <br>Author:董嘉文(roco2015@163.com)
         * <br>Date:2016年5月8日
         * @param msg 需要发送的消息
         */
        public void send(final String msg, final String currentUser){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    chatBo.send(msg,currentUser);
                    Log.i("ChatService", "消息已发送");
                }
            }).start();
        }
    }
    
	/**
	 * <br>Description:TODO 服务绑定后执行，具体执行方法在内部类ChatServicBinder中实现
	 * <br>Author:董嘉文(roco2015@163.com)
	 * <br>Date:2016年5月9日
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return csBinder;
	}

    /**
     * <br>Description:TODO 用来实现接收消息的方法,服务开启后执行
     * <br>Author:董嘉文(roco2015@163.com)
     * <br>Date:2016年5月8日
     * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
     */
	
    /**
     * <br>Description:TODO 第一个线程用于接收服务器的数据，第二个线程用于接收其他客户端数据
     * <br>Author:董嘉文(roco2015@163.com)
     * <br>Date:2016年5月30日
     * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        //chatBo.receive();
        
        
        
        new Thread(new Runnable() {

            @Override
            public void run() {
                chatBo = new ChatBoImpl();
                Log.i("ChatService", "udp接收器启动");
                // TODO Auto-generated method stub
                Intent intentRec = new Intent("cc.heicoco.Chat.broadcast.RECEIVE");
                LinkedList<Friend> friendList = MyGlobal.getFriendList();
                Log.i("ChatService", "udp准备工作完毕");
                while (true) {
                    Log.i("ChatService", "等待其他人的消息");
                    chatBo.receive(friendList);
                    sendBroadcast(intentRec);
                    Log.i("ChatService", "接收到新消息");
                }
            }
        }).start();
        return super.onStartCommand(intent, Service.START_REDELIVER_INTENT, startId);  
    }

    /**
     * <br>Description:TODO 清理套接字
     * <br>Author:董嘉文(roco2015@163.com)
     * <br>Date:2016年5月8日
     * @see android.app.Service#onDestroy()
     */
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        chatBo.close();
    }

}
