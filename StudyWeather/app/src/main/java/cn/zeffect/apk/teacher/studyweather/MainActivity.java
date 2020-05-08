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
        fragments.add(new LocationFragment());
        fragments.add(new WeatherFragment().setCityCode("520625").setCityName("印江土家族苗族自治县"));
        fragments.add(new WeatherFragment().setCityCode("520627").setCityName("沿河土家族自治县"));
        fragments.add(new WeatherFragment().setCityCode("520628").setCityName("松桃苗族自治县"));
        fragments.add(new WeatherFragment().setCityCode("520624").setCityName("思南县"));
        WeatherAdapter adapter = new WeatherAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(adapter);
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
