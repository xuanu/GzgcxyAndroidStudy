package cn.zeffect.apk.jump.myapplication2.utils;

import android.util.Log;

public class L {
    public static final String TAG = "zeffect";
    public static boolean isDebug = true;

    public static void v(String msg) {
        if (msg == null) {
            return;
        }
        if (isDebug) {
            Log.v(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (msg == null) {
            return;
        }
        if (isDebug) {
            Log.i(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (msg == null) {
            return;
        }
        if (isDebug) {
            Log.d(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (msg == null) {
            return;
        }
        if (isDebug) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (msg == null) {
            return;
        }
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }
}
