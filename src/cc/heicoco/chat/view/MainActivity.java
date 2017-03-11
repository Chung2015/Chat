package cc.heicoco.chat.view;

import java.util.LinkedList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.PopupMenu.OnMenuItemClickListener;
import cc.heicoco.chat.R;
import cc.heicoco.chat.controller.activity.Business;
import cc.heicoco.chat.controller.adapter.FriendAdapter;
import cc.heicoco.chat.controller.service.ChatService;
import cc.heicoco.chat.model.pojo.Friend;
import cc.heicoco.chat.model.utils.MyGlobal;

public class MainActivity extends Activity implements OnClickListener {

    private Button buttonAdd;
    private Button button_talk_team;
    private Button buttonRefresh;
    private ListView friendListView;
    private FriendAdapter adapter;
    private LinkedList<Friend> friendList;

    private Intent intentServer;
    private MsgReceiver msgReceiver;
    private int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAdd = (Button) findViewById(R.id.button_add_friend);
        button_talk_team = (Button) findViewById(R.id.button_new_talk_team);
        friendListView = (ListView) findViewById(R.id.friend_listView);
        buttonRefresh = (Button) findViewById(R.id.button_refresh);

        buttonRefresh.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        button_talk_team.setOnClickListener(this);

        friendList = new LinkedList<Friend>();
        MyGlobal.setFriendList(friendList);

        UpdateFriendListAsyTask updateFriendListAsyTask = new UpdateFriendListAsyTask(MainActivity.this);
        updateFriendListAsyTask.execute();
        
        //注册广播
        IntentFilter intentFilterChatRec = new IntentFilter();
        intentFilterChatRec.addAction("cc.heicoco.Chat.broadcast.RECEIVE");
        msgReceiver = new MsgReceiver();
        registerReceiver(msgReceiver, intentFilterChatRec);

        //启动服务
        intentServer = new Intent(MainActivity.this, ChatService.class);
        startService(intentServer);
        
        //进入聊天界面
        friendListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                index = position;
                ConnFriendAsyTask connFriendAsyTask = new ConnFriendAsyTask(MainActivity.this);
                connFriendAsyTask.execute();
                
            }
        });

        //删除pop菜单
        friendListView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                index = position;
                showPopupMenu();
                return true;
            }
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(msgReceiver);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.button_add_friend:
            Intent intent_add = new Intent(MainActivity.this, AddFriendActivity.class);
            startActivityForResult(intent_add, 1);
            break;
        case R.id.button_new_talk_team:
            break;
        case R.id.button_refresh:
            adapter.notifyDataSetChanged();
            break;
        default:
            break;
        }
    }
    
    private void showPopupMenu() {
        PopupMenu popMenu = new PopupMenu(MainActivity.this,
                friendListView.getChildAt(index));
        popMenu.inflate(R.menu.friendop);
        popMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // TODO Auto-generated method stub
                switch (item.getItemId()) {
                case R.id.popmenu_delete:
                    deleteFriend();
                    break;
                default:
                    break;
                }
                return true;
            }
        });
        popMenu.show();
    }

    private void deleteFriend() {
        DeleteFriendAsyTask deleteFriendAsyTask = new DeleteFriendAsyTask(MainActivity.this);
        deleteFriendAsyTask.execute();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
        case 1:
            adapter.notifyDataSetChanged();
        }
    }

    private class UpdateFriendListAsyTask extends AsyncTask<String, Integer, Boolean> {

        Context mContext;

        ProgressDialog mDialog = null;

        public UpdateFriendListAsyTask(Context context) {
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            Business bsUF = new Business();
            bsUF.getFriend(friendList, ((MyApplication) getApplication()).getCurrentUser().getAccount());
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mDialog.dismiss();
            if (result) {
                adapter = new FriendAdapter(MainActivity.this, R.layout.friend_item, friendList);
                friendListView.setAdapter(adapter);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mDialog = new ProgressDialog(mContext);
            mDialog.setMessage("更新好友列表……");
            mDialog.setCancelable(true);
            mDialog.show();
        }
    }
    
    private class DeleteFriendAsyTask extends AsyncTask<String, Integer, Boolean> {

        Context mContext;

        ProgressDialog mDialog = null;

        public DeleteFriendAsyTask(Context context) {
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            Business bsUF = new Business();
            MyApplication mAppl = (MyApplication)getApplication();
            String account = mAppl.getCurrentUser().getAccount();
            String userName = friendList.get(index).getName();
            return bsUF.deleteFriend(friendList, account, userName);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mDialog.dismiss();
            if (result) {
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mDialog = new ProgressDialog(mContext);
            mDialog.setMessage("删除中……");
            mDialog.setCancelable(true);
            mDialog.show();
        }
    }
    private class ConnFriendAsyTask extends AsyncTask<String, Integer, Boolean> {

        Context mContext;

        ProgressDialog mDialog = null;

        public ConnFriendAsyTask(Context context) {
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            Business bsUF = new Business();
            String userName = friendList.get(index).getName();
            Log.i("MainActivity", "目标联系人用户名"+userName);
            return bsUF.connFriend(userName);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mDialog.dismiss();
            if (result) {
                Toast.makeText(mContext,"连接成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                intent.putExtra("name", friendList.get(index).getName());
                intent.putExtra("index", index);
                startActivity(intent);
            }else{
                Toast.makeText(mContext,"当前联系人不在线", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mDialog = new ProgressDialog(mContext);
            mDialog.setMessage("建立连接中，正在努力穿透net……");
            mDialog.setCancelable(true);
            mDialog.show();
        }
    }
    class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            adapter.notifyDataSetChanged();
        }
    }
}
