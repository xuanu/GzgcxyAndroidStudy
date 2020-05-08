package cn.zeffect.apk.teacher.studyweather;

import android.app.Application;

import com.litesuits.orm.LiteOrm;
import com.zhy.http.okhttp.BuildConfig;

public class MyApp extends Application {

    private static LiteOrm liteOrm;

    @Override
    public void onCreate() {
        super.onCreate();
        if (liteOrm == null) {
            liteOrm = LiteOrm.newSingleInstance(this, "zeffect.db");
            liteOrm.setDebugged(BuildConfig.DEBUG);
        }
    }

    public static LiteOrm getLiteOrm() {
        return liteOrm;
    }
}
