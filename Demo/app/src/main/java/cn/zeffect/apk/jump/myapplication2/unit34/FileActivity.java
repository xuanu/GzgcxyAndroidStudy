package cn.zeffect.apk.jump.myapplication2.unit34;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.zeffect.apk.jump.myapplication2.R;
import cn.zeffect.apk.jump.myapplication2.unit31.PermissionUtils;

public class FileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView showTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);
        findViewById(R.id.print_btn).setOnClickListener(this);
        findViewById(R.id.save_btn).setOnClickListener(this);
        showTv = findViewById(R.id.show_tv);
        findViewById(R.id.read_file_btn).setOnClickListener(this);
        findViewById(R.id.write_file_btn).setOnClickListener(this);
    }

    private void show(String msg) {
        if (msg == null) {
            return;
        }
        showTv.setText(showTv.getText() + "\n" + msg);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.print_btn) {
            //应用内部目录
            File tmpFile = new File(getCacheDir(), "1.txt");
            try {
                tmpFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("zeffect", "内部目录：" + getCacheDir().getAbsolutePath());
            Log.e("zeffect", "外部目录：" + getExternalCacheDir().getAbsolutePath());
            File tmpFile2 = new File(getExternalFilesDir("Docs"), "2.txt");
            try {
                tmpFile2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (v.getId() == R.id.save_btn) {
            //

            //如何在SD卡上创建1.txt的文件？
            if (PermissionUtils.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                create1File();
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1100);
                } else {
                    create1File();
                }
            }
        } else if (v.getId() == R.id.read_file_btn) {
            File readFile = create1File();
            String tmpStr = FileIOandOperation.readFile(readFile, "").toString();
            show("文件的内容为：" + tmpStr);
        } else if (v.getId() == R.id.write_file_btn) {
            File readFile = create1File();
            FileIOandOperation.writeFile(readFile.getAbsolutePath(), "今天是周三，天天都要起早！！！" + System.currentTimeMillis(), true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1100) {
            if (PermissionUtils.hasPermisson(grantResults)) {
                create1File();
            } else {
                Toast.makeText(this, "未授权！！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File create1File() {
        File tmpFile = new File(Environment.getExternalStorageDirectory() + File.separator + "Docs" + File.separator + "Temp" + File.separator + "1.txt");
        try {
            tmpFile.getParentFile().mkdirs();
            tmpFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmpFile;
    }
}
