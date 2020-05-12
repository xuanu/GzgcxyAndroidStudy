package cn.zeffect.apk.teacher.studyweather.city;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.zeffect.apk.teacher.studyweather.MyApp;
import cn.zeffect.apk.teacher.studyweather.R;
import cn.zeffect.apk.teacher.studyweather.city.adapter.CityAdapter;
import cn.zeffect.apk.teacher.studyweather.city.bean.UserCity;
import cn.zeffect.apk.teacher.studyweather.city.chose.CityChoseDialog;

public class CitysActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView cityRecy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citys);
        //
        findViewById(R.id.add_city_img).setOnClickListener(this);
        findViewById(R.id.back_btn).setOnClickListener(this);
        cityRecy = findViewById(R.id.city_recy);
        //1. 设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        cityRecy.setLayoutManager(layoutManager);
        //2. 设置adapter
        List<UserCity> cityList = MyApp.getLiteOrm().query(UserCity.class);
        CityAdapter adapter = new CityAdapter(cityList);
        cityRecy.setAdapter(adapter);
        //3. 可以优化
        cityRecy.setHasFixedSize(true);
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
