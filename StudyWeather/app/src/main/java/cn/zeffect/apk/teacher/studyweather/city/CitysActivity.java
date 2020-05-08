package cn.zeffect.apk.teacher.studyweather.city;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.zeffect.apk.teacher.studyweather.R;
import cn.zeffect.apk.teacher.studyweather.city.chose.CityChoseDialog;

public class CitysActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citys);
        //
        findViewById(R.id.add_city_img).setOnClickListener(this);
        findViewById(R.id.back_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_city_img) {
            new CityChoseDialog().show(getSupportFragmentManager(), CityChoseDialog.class.getName());
        } else if (v.getId() == R.id.back_btn) {
            this.finish();
        }
    }
}
