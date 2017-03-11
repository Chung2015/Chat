package cc.heicoco.chat.view;

import cc.heicoco.chat.R;
import cc.heicoco.chat.model.bo.TcpRecMsgBo;
import cc.heicoco.chat.model.bo.impl.TcpRecMsgBoImpl;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class LogRegActivity extends Activity {


    private LogFragment logFragment;
    private RegFragment regFragment;
    private int currentFragment = 0;
    private FragmentManager fragmentManager;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_reg);

        logFragment = new LogFragment();
        regFragment = new RegFragment();
        fragmentManager = getFragmentManager();

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().add(R.id.container, logFragment)
                    .commit();
        }
        Toast.makeText(LogRegActivity.this, "左右滑动 切换登录注册", Toast.LENGTH_SHORT).show();
        
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                TcpRecMsgBo trmBo = null;
                trmBo = new TcpRecMsgBoImpl();
                // TODO Auto-generated method stub
                trmBo.tcpReceive();
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.log_reg, menu);
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

    /*
    *//**
     * A placeholder fragment containing a simple view.
     */
    /*
     * public static class PlaceholderFragment extends Fragment {
     * 
     * public PlaceholderFragment() { }
     * 
     * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
     * container, Bundle savedInstanceState) { View rootView =
     * inflater.inflate(R.layout.fragment_log, container, false); return
     * rootView; } }
     */

    private int startX = 0, endX = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            startX = (int) event.getX();
            break;
        case MotionEvent.ACTION_UP:
            endX = (int) event.getX();
            break;
        case MotionEvent.ACTION_MOVE:
            return true;
        default:
            break;
        }
        if (endX - startX > 150) {
            if (currentFragment != 0) {
                currentFragment = 0;
                fragmentManager.beginTransaction()
                        .replace(R.id.container, logFragment).commit();
                
            }
        } else if (startX - endX > 150) {
            if (currentFragment != 1) {
                currentFragment = 1;
                fragmentManager.beginTransaction()
                        .replace(R.id.container, regFragment).commit();
            }
        }
        return true;
    }

    public void reg(View v) {
        regFragment.reg(v);
    }

    public void log(View v) {
        logFragment.log(v);
    }
}
