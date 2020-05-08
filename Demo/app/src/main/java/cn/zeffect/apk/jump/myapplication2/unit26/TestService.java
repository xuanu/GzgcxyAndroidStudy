package cn.zeffect.apk.jump.myapplication2.unit26;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class TestService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        //第一次启动调用oncreate
        Log.e("zeffect", "TestService onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //后续每次启动，调用onStartCommand
        Log.e("zeffect", "onStartCommand onCreate");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
