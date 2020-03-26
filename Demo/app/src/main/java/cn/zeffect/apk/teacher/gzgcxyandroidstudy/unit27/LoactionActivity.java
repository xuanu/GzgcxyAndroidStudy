package cn.zeffect.apk.teacher.gzgcxyandroidstudy.unit27;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.zeffect.apk.teacher.gzgcxyandroidstudy.R;

public class LoactionActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaction);
        findViewById(R.id.tmp_de_btn).setOnClickListener(this);
        findViewById(R.id.tmp_tr_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //模拟选择城市之后，返回上一个界面。
        //1.如何给上一个界面返回数据？
        //2.如何结束当前界面？
        if (v.getId() == R.id.tmp_de_btn) {
            Intent tmpIntent = new Intent();
            tmpIntent.putExtra("city", "德江县");
            setResult(Activity.RESULT_OK, tmpIntent);
            LoactionActivity.this.finish();
        } else if (v.getId() == R.id.tmp_tr_btn) {
            Intent tmpIntent = new Intent();
            tmpIntent.putExtra("city", "铜仁市");
            setResult(Activity.RESULT_OK, tmpIntent);
            LoactionActivity.this.finish();
        }
    }
}
