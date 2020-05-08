package cn.zeffect.apk.jump.myapplication2.unit29;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class TestService extends Service {//ALT+ENTER

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();//类创建的时候调用,只会调用一次
        Log.e("zeffect", "Service onCreate!!!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //重复调用的时候，如果有数据，是通过Intent传递过来的。
        Log.e("zeffect", "Service onStartCommand!!!");
        String value = intent.getStringExtra("key");
        Log.e("zeffect", "传递数据为：" + value);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //结束的时候调用
        Log.e("zeffect", "Service onDestroy!!!");
    }
}
