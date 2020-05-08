package cn.zeffect.apk.jump.myapplication2.unit29;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.zeffect.apk.jump.myapplication2.R;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        //
        findViewById(R.id.start_service_btn).setOnClickListener(this);
        findViewById(R.id.stop_service_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_service_btn) {
            //启动服务
            Intent intent = new Intent(this, TestService.class);
            intent.putExtra("key", "你好啊");
            startService(intent);
        } else if (v.getId() == R.id.stop_service_btn) {
            Intent intent = new Intent(this, TestService.class);
            stopService(intent);
        }
    }
}
