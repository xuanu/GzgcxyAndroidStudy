package cn.zeffect.apk.jump.myapplication2.unit27;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.zeffect.apk.jump.myapplication2.R;
import cn.zeffect.apk.jump.myapplication2.unit25.GameInfo;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载界面
        setContentView(R.layout.activity_welcome);
        //
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Activity的跳转
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                intent.putExtra("key1", 1);
                intent.putExtra("key2", "你好啊");
                intent.putExtra("key3", true);
                intent.putExtra("key4", new GameInfo().setName("这是游戏名字").setDownUrl("http://xxxxxx").setImgUrl("http://ssssss"));
//                intent.putExtra("key4", new String("你好啊"));
                startActivity(intent);
                WelcomeActivity.this.finish();//可以结束一个类。
            }
        }, 3000);
    }
}
