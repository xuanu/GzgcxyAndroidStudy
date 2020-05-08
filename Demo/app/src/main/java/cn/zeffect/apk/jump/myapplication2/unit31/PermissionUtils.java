package cn.zeffect.apk.jump.myapplication2.unit31;

import android.content.Context;
import android.content.pm.PackageManager;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

import androidx.core.content.ContextCompat;

public class PermissionUtils {

    /**
     * @param context
     * @param pers    需要检查的权限数组
     * @return
     */
    public static boolean checkPermission(Context context, String... pers) {
        if (context == null) {
            throw new NullPointerException("context 不能为 null !!");
        }
        if (pers.length == 0) {
            return true;
        }
        for (int i = 0; i < pers.length; i++) {
            if (ContextCompat.checkSelfPermission(context, pers[i]) == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }


    public static boolean hasPermisson(int[] grantResults) {
        boolean allGrant = true;
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                allGrant = false;
                break;
            }
        }
        return allGrant;
    }
}
