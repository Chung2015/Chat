package cc.heicoco.chat.view;

import java.util.LinkedList;

import cc.heicoco.chat.R;
import cc.heicoco.chat.controller.activity.Business;
import cc.heicoco.chat.model.pojo.Friend;
import cc.heicoco.chat.model.utils.MyGlobal;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class AddFriendActivity extends Activity {

    private EditText username_text;
    private ListView listView;
    private int index;
    
    private ArrayAdapter<String> adapter;
    private String[] queryAns;
    private Business bsUF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        
        bsUF = new Business();
        
        username_text = (EditText) findViewById(R.id.username_text);
        listView = (ListView) findViewById(R.id.listView_queryfriend);
        
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // TODO Auto-generated method stub
                index = position;
                showPopupMenu();
                return true;
            }
        });
    }

    private void showPopupMenu() {
        PopupMenu popMenu = new PopupMenu(AddFriendActivity.this,
                listView.getChildAt(index));
        popMenu.inflate(R.menu.add_friend_op);
        popMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // TODO Auto-generated method stub
                switch (item.getItemId()) {
                case R.id.popmenu_add:
                    AddAsyTask addAsyTask = new AddAsyTask(AddFriendActivity.this);
                    addAsyTask.execute();
                    break;
                default:
                    break;
                }
                return true;
            }
        });
        popMenu.show();
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

    /**
     * <br>Description:TODO 查询按钮触发
     * <br>Author:董嘉文(roco2015@163.com)
     * <br>Date:2016年5月28日
     * @param v
     */
    public void query(View v) {
        QueryAsyTask queryAsyTask = new QueryAsyTask(AddFriendActivity.this);
        queryAsyTask.execute();
    }

    private class QueryAsyTask extends AsyncTask<String, Integer, Boolean> {

        Context mContext;
        ProgressDialog mDialog = null;

        public QueryAsyTask(Context context) {
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            queryAns = bsUF.queryFriend(username_text.getText().toString());
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mDialog.dismiss();

            if (result) {
                adapter = new ArrayAdapter<String>(AddFriendActivity.this,
                        android.R.layout.simple_list_item_1, queryAns);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(mContext, "没有符合的目标", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(mContext);
            mDialog.setMessage("搜索中 请稍等……");
            mDialog.setCancelable(true);
            mDialog.show();
        }
    }
    
    private class AddAsyTask extends AsyncTask<String, Integer, Integer> {

        Context mContext;
        ProgressDialog mDialog = null;

        public AddAsyTask(Context context) {
            mContext = context;
        }

        @Override
        protected Integer doInBackground(String... arg0) {
            LinkedList<Friend> friendList = MyGlobal.getFriendList();
            return bsUF.addFriend(friendList, 
                    ((MyApplication)getApplication()).getCurrentUser().getUserName(), 
                    queryAns[index]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            mDialog.dismiss();

            if (result == 0) {
                Toast.makeText(AddFriendActivity.this, "添加成功",Toast.LENGTH_SHORT).show();
            }else if(result == 1){
                Toast.makeText(AddFriendActivity.this,"该联系人已添加", Toast.LENGTH_SHORT).show();
            }else if(result == 2){
                Toast.makeText(AddFriendActivity.this,"添加失败，请联系管理员", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(mContext);
            mDialog.setMessage("添加中 请稍等……");
            mDialog.setCancelable(true);
            mDialog.show();
        }
    }
}