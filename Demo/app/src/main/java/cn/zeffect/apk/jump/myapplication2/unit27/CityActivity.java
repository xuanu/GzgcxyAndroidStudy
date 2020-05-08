package cn.zeffect.apk.jump.myapplication2.unit27;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.zeffect.apk.jump.myapplication2.R;

public class CityActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        //
        findViewById(R.id.gy_btn).setOnClickListener(this);
        findViewById(R.id.tr_btn).setOnClickListener(this);
        findViewById(R.id.zy_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.gy_btn) {
            setResult(Activity.RESULT_OK, new Intent().putExtra("city", "贵阳"));
            CityActivity.this.finish();
        } else if (v.getId() == R.id.tr_btn) {
            setResult(Activity.RESULT_OK, new Intent().putExtra("city", "铜仁"));
            finish();
        } else if (v.getId() == R.id.zy_btn) {
            setResult(Activity.RESULT_OK, new Intent().putExtra("city", "遵义"));
            finish();
        }
    }
}
