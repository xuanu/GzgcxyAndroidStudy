package cn.zeffect.apk.jump.myapplication2;

import android.app.Application;
import android.util.Log;

import com.litesuits.orm.LiteOrm;

/**
 * 他的生命周期从应用开始直到应用结束
 */
public class MyApp extends Application {

    public static LiteOrm liteOrm;

    @Override
    public void onCreate() {
        super.onCreate();
        //
        if (liteOrm == null) {
            liteOrm = LiteOrm.newSingleInstance(this, "liteorm.db");
        }
        liteOrm.setDebugged(true); // open the log
    }
}
