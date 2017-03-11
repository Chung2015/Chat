package cc.heicoco.chat.view;

import java.util.LinkedList;
import java.util.List;

import cc.heicoco.chat.R;
import cc.heicoco.chat.controller.adapter.MsgAdapter;
import cc.heicoco.chat.controller.service.ChatService;
import cc.heicoco.chat.controller.service.ChatService.ChatServicBinder;
import cc.heicoco.chat.model.pojo.Friend;
import cc.heicoco.chat.model.pojo.Msg;
import cc.heicoco.chat.model.utils.MyGlobal;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ChatActivity extends Activity {

    private EditText inputText;
    private String content;
    private List<Msg> msgList;
    private MsgAdapter adapter;
    private ListView msgListView;
    private Intent bindIntent;
    private ChatService.ChatServicBinder csBinder;
    private String currentUserName;
    private MsgReceiver msgReceiver;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		
		setTitle(getIntent().getStringExtra("name"));
        LinkedList<Friend> friendList = MyGlobal.getFriendList();
        int i = getIntent().getIntExtra("index", -1);
        if(i == -1){
            Toast.makeText(ChatActivity.this, "严重错误，错误代码001", Toast.LENGTH_SHORT).show();
        }
        msgList = friendList.get(i).getMsgList();
        adapter = new MsgAdapter(ChatActivity.this, R.layout.msg_item, msgList);
        msgListView = (ListView) findViewById(R.id.msg_list_view);
        msgListView.setAdapter(adapter);
        
        inputText = (EditText) findViewById(R.id.input_text);
        bindIntent = new Intent(ChatActivity.this, ChatService.class);

        //注册广播
        IntentFilter intentFilterChatRec = new IntentFilter();
        intentFilterChatRec.addAction("cc.heicoco.Chat.broadcast.RECEIVE");
        msgReceiver = new MsgReceiver();
        registerReceiver(msgReceiver, intentFilterChatRec);
	}

	@Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        currentUserName = ((MyApplication)getApplication()).getCurrentUser().getUserName();
        bindService(bindIntent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        unbindService(connection);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(msgReceiver);
    }


    private ServiceConnection connection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            csBinder = (ChatServicBinder) service;
            Log.d("chatActivity", "服务已绑定");
            
        }

        /**
         * <br>Description:TODO Android系统在同service的连接意外丢失时调用这个．比如当service崩溃了或被强杀了．当客户端解除绑定时，这个方法不会被调用。
         * <br>Author:董嘉文(roco2015@163.com)
         * <br>Date:2016年5月9日
         * @see android.content.ServiceConnection#onServiceDisconnected(android.content.ComponentName)
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            csBinder = null;
            Log.i("chatActivity", "服务连接意外丢失");
        }
	    
	};
	
	public void send_onclick(View v){
	    content = inputText.getText().toString();
	    if (!"".equals(content)) {
            Msg msg = new Msg(content, Msg.TYPE_SENT);
            msgList.add(msg);
            adapter.notifyDataSetChanged();
            msgListView.setSelection(msgList.size());
            inputText.setText("");
            csBinder.send(content,currentUserName);
        }
	}
	
	
	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chat, menu);
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            adapter.notifyDataSetChanged();
        }
    }
}
