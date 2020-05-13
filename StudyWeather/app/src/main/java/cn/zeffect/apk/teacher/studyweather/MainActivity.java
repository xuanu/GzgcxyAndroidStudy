package cn.zeffect.apk.teacher.studyweather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import cn.zeffect.apk.teacher.studyweather.bean.Weather;
import cn.zeffect.apk.teacher.studyweather.city.CitysActivity;
import cn.zeffect.apk.teacher.studyweather.city.bean.UserCity;
import cn.zeffect.apk.teacher.studyweather.location.LocationFragment;
import cn.zeffect.apk.teacher.studyweather.location.WeatherFragment;
import cn.zeffect.apk.teacher.studyweather.utils.FileIOandOperation;
import cn.zeffect.apk.teacher.studyweather.utils.SpUtils;
import okhttp3.Call;
import okhttp3.Request;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager pager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        pager = findViewById(R.id.view_pager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new LocationFragment());//定位城市
        List<UserCity> cityList = MyApp.getLiteOrm().query(UserCity.class);
        for (int i = 0; i < cityList.size(); i++) {
            UserCity userCity = cityList.get(i);
            if (!userCity.getType().equals(UserCity.TYPE_LOCATION)) {
                fragments.add(new WeatherFragment().setCityName(userCity.getCityname()).setCityCode(userCity.getAdcode()));
            }
        }
        WeatherAdapter adapter = new WeatherAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(cityList.size());
        //
        findViewById(R.id.sel_city_img).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sel_city_img) {
            startActivity(new Intent(this, CitysActivity.class));
        }
    }


    public static class WeatherAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        public WeatherAdapter(@NonNull FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
