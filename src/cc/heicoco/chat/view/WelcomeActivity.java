package cc.heicoco.chat.view;

import cc.heicoco.chat.R;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class WelcomeActivity extends Activity {
    
    private ImageView imageView_welpic;
    private ImageView hello_pic_left;
    private ImageView hello_pic_right;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        
        imageView_welpic = (ImageView)findViewById(R.id.imageView_welpic);
        hello_pic_left = (ImageView)findViewById(R.id.imageView_welleft);
        hello_pic_right = (ImageView)findViewById(R.id.imageView_welright);
        
        ((AnimationDrawable)hello_pic_left.getDrawable()).start();
        ((AnimationDrawable)hello_pic_right.getDrawable()).start();
        
        propertyValuesHolder0(imageView_welpic);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
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
    
    public void propertyValuesHolder1(View view){
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 0, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 0, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(800).start();
    }
    public void propertyValuesHolder0(View view){
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("rotationX", 1080f, 0f, 360.0f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX).setDuration(500).start();
    }
    
    public void skip(View v){
        if(networkstatus()){
            Intent intent = new Intent(WelcomeActivity.this, LogRegActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(WelcomeActivity.this, "没有开启任何网络连接哦", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ((AnimationDrawable)hello_pic_left.getDrawable()).stop();
        ((AnimationDrawable)hello_pic_right.getDrawable()).stop();
    }
    
    public boolean networkstatus(){
        NetworkInfo networkInfo = ((ConnectivityManager)getSystemService
                (Context.CONNECTIVITY_SERVICE)).
                getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isAvailable()){
            return true;
        }else{
            return false;
        }
    }
}
