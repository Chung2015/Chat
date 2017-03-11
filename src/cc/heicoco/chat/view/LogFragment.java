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

public class LogFragment extends Fragment {
    
    private EditText username_edt;
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
        View view = inflater.inflate(R.layout.fragment_log, container, false);
        username_edt = (EditText)view.findViewById(R.id.text_log_username2);
        password_edt = (EditText)view.findViewById(R.id.text_log_password2);
        return view;
    }

    public void log(View v){
        LogAsyTask task = new LogAsyTask(getActivity());
        task.execute();
    }
    
    private class LogAsyTask extends AsyncTask<String, Integer, String>{

        Context mContext;
        ProgressDialog mDialog = null;
        
        private LogAsyTask(Context mContext) {
            super();
            // TODO Auto-generated constructor stub
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... arg0) {
            User user = new User(username_edt.getText().toString(),password_edt.getText().toString(),"","");
            String result = bsC.log(user);
            if (result.equals(MyGlobal.getOpsucceed())) {
                MyApplication mAppl = (MyApplication) getActivity().getApplication();
                mAppl.setCurrentUser(user);
                MyGlobal.setCurrentUser(user);
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mDialog.dismiss();
            if (result.equals(MyGlobal.getOpsucceed())) {
                Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }else if (result.equals(MyGlobal.getOpfailNotheuser())){
                Toast.makeText(mContext, "登录失败，用户名不存在", Toast.LENGTH_SHORT).show();
            }else if (result.equals(MyGlobal.getOpfailWrongpassword())){
                Toast.makeText(mContext, "登录失败，密码错误", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(mContext, "登录失败，未知错误", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mDialog = new ProgressDialog(mContext);
            mDialog.setMessage("登录中 请稍等……");
            mDialog.setCancelable(false);
            mDialog.show();
        }
        
    }
}
