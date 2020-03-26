package cn.zeffect.apk.teacher.gzgcxyandroidstudy.unit27;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.zeffect.apk.teacher.gzgcxyandroidstudy.R;

public class WecomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_welcome);
        //接收Intent数据
        String key1 = getIntent().getStringExtra("key1");
        int key2 = getIntent().getIntExtra("key", -1);
        Log.e("zeffect", "key1:" + key1);
        Log.e("zeffect", "key2:" + key2);
        Log.e("zeffect", "有无key3:" + getIntent().hasExtra("key3"));
    }
}
