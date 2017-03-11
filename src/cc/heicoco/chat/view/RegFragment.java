package cc.heicoco.chat.view;

import cc.heicoco.chat.R;
import cc.heicoco.chat.controller.activity.Business;
import cc.heicoco.chat.model.entity.User;
import cc.heicoco.chat.model.utils.MyGlobal;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class RegFragment extends Fragment {
    
    private EditText username_edt;
    private EditText account_edt;
    private EditText password_edt;
    private Business bsC = new Business();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_reg, container, false);
        account_edt = (EditText)view.findViewById(R.id.text_reg_account2);
        username_edt = (EditText)view.findViewById(R.id.text_reg_username2);
        password_edt = (EditText)view.findViewById(R.id.text_reg_password2);
        return view;
    }
    
    /**
     * <br>Description:TODO 注册按钮的单击事件
     * <br>Author:董嘉文(roco2015@163.com)
     * <br>Date:2016年5月9日
     * @param v
     */
    public void reg(View v){
        RegAsyTask task = new RegAsyTask(getActivity());
        task.execute();
    }
    
    /**
     * <br>Title:TODO 异步处理类
     * <br>Description:TODO 将注册过程从主线程移到子线程（android自带封装好的），真的很方便，不过结构就有点乱了，这个应该是controller做的事情
     * <br>Author:董嘉文(roco2015@163.com)
     * <br>Date:2016年5月9日
     */
    private class RegAsyTask extends AsyncTask<String, Integer, String>{

        Context mContext;
        ProgressDialog mDialog = null;
        
        private RegAsyTask(Context mContext) {
            super();
            // TODO Auto-generated constructor stub
            this.mContext = mContext;
        }

        /**
         * <br>Description:TODO 调用controller进行注册，返回值0为注册成功，1为用户名重复
         * <br>Author:董嘉文(roco2015@163.com)
         * <br>Date:2016年5月9日
         * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
         */
        @Override
        protected String doInBackground(String... arg0) {
            User user = new User(account_edt.getText().toString(), password_edt.getText().toString(),username_edt.getText().toString(),"");
            String result = bsC.reg(user);
            if (result.equals(MyGlobal.getOpsucceed())) {
                MyApplication mAppl = (MyApplication) getActivity().getApplication();
                mAppl.setCurrentUser(user);
                MyGlobal.setCurrentUser(user);
            }
            return result;
        }

        /**
         * <br>Description:TODO doInBackground的返回值会传入此函数中，在此函数中会判断执行结果
         * <br>Author:董嘉文(roco2015@163.com)
         * <br>Date:2016年5月12日
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mDialog.dismiss();
            if (result.equals(MyGlobal.getOpsucceed())) {
                Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }else if (result.equals(MyGlobal.getOpfailAccountsamed())){
                Toast.makeText(mContext, "注册失败，用户名已存在", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(mContext, "注册失败，未知错误", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mDialog = new ProgressDialog(mContext);
            mDialog.setMessage("注册中 请稍等……");
            mDialog.setCancelable(false);
            mDialog.show();
        }
        
    }
}
