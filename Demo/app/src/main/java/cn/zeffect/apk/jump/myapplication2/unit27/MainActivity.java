package cn.zeffect.apk.jump.myapplication2.unit27;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.zeffect.apk.jump.myapplication2.R;
import cn.zeffect.apk.jump.myapplication2.unit25.GameInfo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button cityBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1);
        //getIntent()接收数据
        //1. getIntent().hasExtra("key1") 有没有key1
        Log.e("zeffect", "有没有key1：" + getIntent().hasExtra("key1"));
        Log.e("zeffect", "有没有key5：" + getIntent().hasExtra("key5"));
        //key1 原本是一个Int类型，如果我非要用string接收，会有什么问题？
        Log.e("zeffect", "key1:" + getIntent().getIntExtra("key1", -1));
        Log.e("zeffect", "key2:" + getIntent().getStringExtra("key2"));
        Log.e("zeffect", "key3:" + getIntent().getBooleanExtra("key3", true));
        if (getIntent().hasExtra("key4")) {
            if (getIntent().getSerializableExtra("key4") instanceof GameInfo) {
                GameInfo tmpGame = (GameInfo) getIntent().getSerializableExtra("key4");
                Log.e("zeffectt", ":" + tmpGame.getName() + "," + tmpGame.getDownUrl());
            }
        }
        //
        cityBtn = findViewById(R.id.chose_city_btn);
        cityBtn.setOnClickListener(this);
    }

    //如何拦截返回按键？"

    private long lastClickTime = 0;//上一次点击的时间

    @Override
    public void onBackPressed() {
        long nowTime = System.currentTimeMillis();
        if (nowTime - lastClickTime < 2000) {
            super.onBackPressed();//退出程序
        } else {
            Toast.makeText(this, "再次点击，退出程序！", Toast.LENGTH_SHORT).show();
            lastClickTime = nowTime;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == cityBtn) {
            //如何需要返回数据，可以调用这个方法
//            startActivity();
            //requestCode 用来区分操作的
            startActivityForResult(new Intent(MainActivity.this, CityActivity.class), 110);
        }
    }
    //返回到上一个界面之后，如何接收？

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 110) {
            //我是去选择CITY
            //怎么判断，我有没有选择？
            if (resultCode == Activity.RESULT_OK) {
                String city = data.getStringExtra("city");
                cityBtn.setText("你选择的是：" + city);
            } else {
                cityBtn.setText("请选择地区！！！");
            }
        }
    }
}
