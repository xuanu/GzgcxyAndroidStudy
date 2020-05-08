package cn.zeffect.apk.jump.myapplication2.untit30;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cn.zeffect.apk.jump.myapplication2.R;

public class BroadActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView showTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad);
        //
        //注册广播
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("zeffect.cn.test.1");
        filter1.addAction("zeffect.cn.test.2");
        filter1.addAction("zeffect.cn.test.3");
        filter1.setPriority(100);
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver1, filter1);
        //
        showTv = findViewById(R.id.show_tv);
        findViewById(R.id.send_btn).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("zeffect", "BroadActivity onDestroy");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver1);
    }

    private BroadcastReceiver myReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            show("1接收到广播：" + intent.getAction() + "|||" + System.currentTimeMillis());
        }
    };


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_btn) {
            //发送广播
            Intent intent = new Intent("zeffect.cn.test.1");
            sendOrderedBroadcast(intent,null);
                    }
    }


    private void show(String str) {
        if (str == null) {
            return;
        }
        showTv.setText(showTv.getText() + "\n" + str);
    }
}
