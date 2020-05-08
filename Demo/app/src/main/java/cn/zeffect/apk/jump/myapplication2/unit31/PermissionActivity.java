package cn.zeffect.apk.jump.myapplication2.unit31;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import cn.zeffect.apk.jump.myapplication2.BuildConfig;
import cn.zeffect.apk.jump.myapplication2.R;
import cn.zeffect.apk.jump.myapplication2.utils.L;

public class PermissionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button openBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        openBtn = findViewById(R.id.open_camera_btn);
        openBtn.setOnClickListener(this);//ALT+ENTER
        L.isDebug = BuildConfig.DEBUG;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.open_camera_btn) {
            //动态申请摄像头的权限
            if (PermissionUtils.checkPermission(this, Manifest.permission.CAMERA)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 1001);
                } else {
                    openCamera();
                }
            } else {
                openCamera();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            if (PermissionUtils.hasPermisson(grantResults)) {
                openCamera();
            } else {
                Toast.makeText(this, "权限未同意！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera() {
        openBtn.setText("成功打开"); //CTRL+F 查找
        Toast.makeText(this, "成功打开摄像头！！", Toast.LENGTH_SHORT).show();
        L.v("成功打开摄像头：" + System.currentTimeMillis());
        L.i("成功打开摄像头：" + System.currentTimeMillis());
        L.d("成功打开摄像头：" + System.currentTimeMillis());
        L.w("成功打开摄像头：" + System.currentTimeMillis());
        L.e("成功打开摄像头：" + System.currentTimeMillis());
    }
}
