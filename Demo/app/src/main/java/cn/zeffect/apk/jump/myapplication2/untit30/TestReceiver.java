package cn.zeffect.apk.jump.myapplication2.untit30;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TestReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "静态注册：" + intent.getAction(), Toast.LENGTH_SHORT).show();
    }
}
