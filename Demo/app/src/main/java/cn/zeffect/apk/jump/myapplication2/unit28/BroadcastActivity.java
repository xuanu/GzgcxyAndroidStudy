package cn.zeffect.apk.jump.myapplication2.unit28;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cn.zeffect.apk.jump.myapplication2.R;

public class BroadcastActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        // 有序广播有一个优先级的问题
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("zeffect.action.1");//这个action就是我们感兴趣的广播名字
        filter1.addAction("zeffect.action.2");
        filter1.addAction("zeffect.action.3");
        filter1.addAction("zeffect.action.xx");
        filter1.setPriority(100);
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver1, filter1);//context的注册方式
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("zeffect.action.1");//这个action就是我们感兴趣的广播名字
        filter2.addAction("zeffect.action.2");
        filter2.addAction("zeffect.action.3");
        filter2.addAction("zeffect.action.xx");
        filter2.setPriority(99);
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver2, filter2);//context的注册方式
        IntentFilter filter3 = new IntentFilter();
        filter3.addAction("zeffect.action.1");//这个action就是我们感兴趣的广播名字
        filter3.addAction("zeffect.action.2");
        filter3.addAction("zeffect.action.3");
        filter3.addAction("zeffect.action.xx");
        filter3.setPriority(98);
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver3, filter3);//context的注册方式

        //
        findViewById(R.id.send_broad_btn).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver1);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver2);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver3);
    }

    private BroadcastReceiver myReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("zeffect", "1接收到广播，广播名为：" + intent.getAction() + ",时间：" + System.currentTimeMillis());
            Log.e("zeffect", "1接收到数据：" + intent.getStringExtra("word"));
            //

        }
    };
    private BroadcastReceiver myReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("zeffect", "2接收到广播，广播名为：" + intent.getAction() + ",时间：" + System.currentTimeMillis());
            Log.e("zeffect", "2接收到数据：" + intent.getStringExtra("word"));
        }
    };
    private BroadcastReceiver myReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("zeffect", "3接收到广播，广播名为：" + intent.getAction() + ",时间：" + System.currentTimeMillis());
            Log.e("zeffect", "3接收到数据：" + intent.getStringExtra("word"));
        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_broad_btn) { // 发送广播
            Intent intent = new Intent("zeffect.action.xx");//action为需要发送的广播名称
            intent.putExtra("word", "hello world !~!!");
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent); // 无序广播
        }
    }
}
