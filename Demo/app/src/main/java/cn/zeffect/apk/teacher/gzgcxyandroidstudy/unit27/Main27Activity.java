package cn.zeffect.apk.teacher.gzgcxyandroidstudy.unit27;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.zeffect.apk.teacher.gzgcxyandroidstudy.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main27Activity extends AppCompatActivity implements View.OnClickListener {

    private Button cityBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.go_welcome_btn).setOnClickListener(this);//ALT+ENTER
        cityBtn = findViewById(R.id.go_city_btn);
        cityBtn.setOnClickListener(this);
        findViewById(R.id.call_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.go_welcome_btn) {
            //跳转Activity
            Intent intent = new Intent(Main27Activity.this, WecomeActivity.class);
            intent.putExtra("key1", "string1");
            intent.putExtra("key", 128);
            startActivityForResult(intent, 500);
        } else if (v == cityBtn) {
            Intent intent = new Intent(Main27Activity.this, LoactionActivity.class);
            startActivityForResult(intent, 600);
            //当你去不同界面选择时，要通requestCode来判断。
        } else if (v.getId() == R.id.call_btn) {

        }
    }

    //如何接收返回数据？

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 600) {
            if (resultCode == Activity.RESULT_OK) {
                String city = data.getStringExtra("city");
                cityBtn.setText("你选择的是：" + city);
            } else {
                cityBtn.setText("请选择城市");
            }
        }
    }
}
