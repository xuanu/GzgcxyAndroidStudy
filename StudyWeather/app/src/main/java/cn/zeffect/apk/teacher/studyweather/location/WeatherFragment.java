package cn.zeffect.apk.teacher.studyweather.location;

import android.Manifest;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import cn.zeffect.apk.teacher.studyweather.MyApp;
import cn.zeffect.apk.teacher.studyweather.R;
import cn.zeffect.apk.teacher.studyweather.bean.Weather;
import cn.zeffect.apk.teacher.studyweather.city.bean.UserCity;
import cn.zeffect.apk.teacher.studyweather.utils.FileIOandOperation;
import cn.zeffect.apk.teacher.studyweather.utils.SpUtils;
import okhttp3.Call;
import okhttp3.Request;

public class WeatherFragment extends Fragment implements View.OnClickListener {

    private String cityName, cityCode;

    public WeatherFragment setCityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }

    public WeatherFragment setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weatherBg = view.findViewById(R.id.weather_bg);
        cityTv = view.findViewById(R.id.city_tv);
        tempTv = view.findViewById(R.id.temp_tv);
        weatherTv = view.findViewById(R.id.weather_tv);
        windDirTv = view.findViewById(R.id.wind_dir_tv);
        humTv = view.findViewById(R.id.hum_tv);
        refreshImg = view.findViewById(R.id.refresh_img);
        bottomLayout = view.findViewById(R.id.bottom_layout);
        //旋转动画
        animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(3000);
        animation.setRepeatCount(-1);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatMode(Animation.RESTART);
        refreshImg.startAnimation(animation);
        refreshImg.setOnClickListener(this);
        handler.sendEmptyMessage(1002);
        //
        cityTv.setText(cityName);
        if (!TextUtils.isEmpty(cityCode)) {
            adcode = cityCode;
            getWeather(cityCode);
        }
    }

    private TextView cityTv, tempTv, weatherTv, windDirTv, humTv;
    private ImageView refreshImg;
    RotateAnimation animation;
    private LinearLayout bottomLayout;
    private ImageView weatherBg;

    public static final String SP_AD_CODE_KEY = "sp_adcode_key";
    public static final String SP_CITY_NAME_KEY = "sp_city_name_key";


    /**
     * 用来保存定位成功之后的adcode
     */
    private String adcode;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.refresh_img) {
            if (!TextUtils.isEmpty(adcode)) {
                if (get1finish && get2finish) { //两个请求结束，才进行新的请求
                    OkHttpUtils.getInstance().cancelTag("weather");
                    refreshImg.startAnimation(animation);
                    getWeather(adcode, false);
                }
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    /**
     * 就可以用来判断，两个请求是否结束
     */
    private boolean get1finish = false, get2finish = false;

    /**
     * 默认调用这个方法，使用缓存
     *
     * @param adCode
     */
    private void getWeather(String adCode) {
        getWeather(adCode, true);
    }

    /**
     * 获取天气
     *
     * @param adCode   指定城市
     * @param useCache 可以指定是否使用缓存
     */
    private void getWeather(String adCode, boolean useCache) {
        if (TextUtils.isEmpty(adCode)) {
            return;
        }
        Date newDate = new Date();
        String day = (newDate.getMonth() + 1) + "月" + newDate.getDate() + "日";
        final File baseCacheFile = new File(getContext().getExternalCacheDir(), day + "base.weather" + adCode);
        if (baseCacheFile.exists() && useCache) { //有缓存直接读取缓存
            String response = FileIOandOperation.readFile(baseCacheFile, "").toString();
            parseBaseWeather(response);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    get1finish = true;
                    animation.cancel();
                }
            }, 1000);
        } else {
            //通过OKHttpUtils获取天气
            OkHttpUtils.get()
                    .tag("weather")
                    .url("https://restapi.amap.com/v3/weather/weatherInfo")
                    .addParams("key", "9d930a1bfb0b3b59568ad9b43399ac3b")
                    .addParams("city", "" + adCode)
                    .addParams("extensions", "base")
                    .build()
                    .execute(new StringCallback() {

                        @Override
                        public void onBefore(Request request, int id) {
                            super.onBefore(request, id);
                            Log.e("zeffect", "" + System.currentTimeMillis());
//                        refreshImg.setVisibility(View.VISIBLE);
                            handler.sendEmptyMessage(1002);
                            tempTv.setText("-°");
                            weatherTv.setText("天气：-");
                            windDirTv.setText("风向：-");
                            humTv.setText("湿度：-");
                            get1finish = false;
                        }

                        @Override
                        public void onAfter(int id) {
                            super.onAfter(id);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    animation.cancel();
                                    handler.sendEmptyMessage(1001);
                                    get1finish = true;
                                }
                            }, 1000);
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(getContext(), "获取天气失败！！", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            //天气获取成功,存入文件
                            FileIOandOperation.writeFile(baseCacheFile, response);
                            //解析一下天气结果
                            parseBaseWeather(response);
                        }
                    });
        }
        final File allCacheFile = new File(getContext().getExternalCacheDir(), day + "all.weather" + adCode);
        if (allCacheFile.exists() && useCache) {
            String response = FileIOandOperation.readFile(allCacheFile, "").toString();
            parseAllWeather(response);
            get2finish = true;
        } else {
            //通过API获取预报天气
            OkHttpUtils.get()
                    .tag("weather")
                    .url("https://restapi.amap.com/v3/weather/weatherInfo")
                    .addParams("key", "9d930a1bfb0b3b59568ad9b43399ac3b")
                    .addParams("city", "" + adCode)
                    .addParams("extensions", "all")
                    .build().execute(new StringCallback() {

                @Override
                public void onBefore(Request request, int id) {
                    super.onBefore(request, id);
                    get2finish = false;
                }

                @Override
                public void onAfter(int id) {
                    super.onAfter(id);
                    get2finish = true;
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    Toast.makeText(getContext(), "获取预报天气失败，请检查网络！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String response, int id) {
                    FileIOandOperation.writeFile(allCacheFile, response);
                    //成功获取预报天气
                    parseAllWeather(response);
                }
            });
        }
    }

    /**
     * 动态生成N个布局，平分宽度
     *
     * @param weathers
     */
    private void addWeather(List<Weather> weathers) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        bottomLayout.removeAllViews();
        for (int i = 0; i < weathers.size(); i++) {
            Weather tmpWeather = weathers.get(i);
            View tmpView = LayoutInflater.from(getContext()).inflate(R.layout.item_all_weather_layout, null);
            TextView tmpTv = tmpView.findViewById(R.id.item_temp_tv);
            tmpTv.setText(tmpWeather.getDaytemp() + "°");
            TextView dayTv = tmpView.findViewById(R.id.day_tv);
            dayTv.setText(tmpWeather.getDate().substring(5));
            ImageView weatherImg = tmpView.findViewById(R.id.item_weather_img);
            //根据天气状态设置状态
            String dayWeather = tmpWeather.getDayweather();
            if (dayWeather.equals("晴")) {
                weatherImg.setImageResource(R.drawable.skyicon_sunshine);
            } else if (dayWeather.equals("少云")
                    || dayWeather.equals("多云")
                    || dayWeather.equals("阴")) {
                weatherImg.setImageResource(R.drawable.skyicon_cloud);
            } else if (dayWeather.equals("晴间多云")) {
                weatherImg.setImageResource(R.drawable.skyicon_partly_cloud);
            } else if (dayWeather.equals("霾")) {
                weatherImg.setImageResource(R.drawable.skyicon_haze);
            } else if (dayWeather.equals("中度霾")) {
                weatherImg.setImageResource(R.drawable.skyicon_haze_light);
            } else if (dayWeather.equals("重度霾")
                    || dayWeather.equals("严重霾")) {
                weatherImg.setImageResource(R.drawable.skyicon_haze_heavy);
            } else if (dayWeather.equals("小雨")
                    || dayWeather.equals("毛毛雨")
                    || dayWeather.equals("细雨")
                    || dayWeather.equals("阵雨")) {
                weatherImg.setImageResource(R.drawable.skyicon_rain_light);
            } else if (dayWeather.equals("雨")
                    || dayWeather.equals("雷阵雨")
                    || dayWeather.equals("中雨")
                    || dayWeather.equals("小雨-中雨")) {
                weatherImg.setImageResource(R.drawable.skyicon_rain_normal);
            }
            bottomLayout.addView(tmpView, params);
        }
    }


    /**
     * 解析预报天气
     *
     * @param response
     */
    private void parseAllWeather(String response) {
        try {
            JSONObject resJson = new JSONObject(response);
            String infocode = resJson.optString("infocode");
            if (infocode.equals("10000")) {
                JSONArray foreArray = resJson.optJSONArray("forecasts");
                JSONObject foreJson = foreArray.optJSONObject(0);
                JSONArray castArray = foreJson.optJSONArray("casts");
                List<Weather> weathers = new ArrayList<>(castArray.length());
                for (int i = 0; i < castArray.length(); i++) {
                    JSONObject castJson = castArray.optJSONObject(i);
                    Weather tmpWeather = new Weather();
                    tmpWeather.setDate(castJson.optString("date"));
                    tmpWeather.setDaypower(castJson.optString("daypower"));
                    tmpWeather.setDaytemp(castJson.optString("daytemp"));
                    tmpWeather.setDayweather(castJson.optString("dayweather"));
                    tmpWeather.setDaywind(castJson.optString("daywind"));
                    tmpWeather.setNightpower(castJson.optString("nightpower"));
                    tmpWeather.setNighttemp(castJson.optString("nighttemp"));
                    tmpWeather.setNightweather(castJson.optString("nightweather"));
                    tmpWeather.setNightwind(castJson.optString("nightwind"));
                    tmpWeather.setWeek(castJson.optString("week"));
                    weathers.add(tmpWeather);
                }
                //
//                        for (int i = 0; i < weathers.size(); i++) {
//                            Weather tmpWeather = weathers.get(i);
//                            Log.e("zeffect", tmpWeather.getDate() + ",天气：" + tmpWeather.getDayweather());
//                        }
                addWeather(weathers);
            } else {
                Toast.makeText(getContext(), "加载失败！", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析 实况天气
     *
     * @param response
     */
    private void parseBaseWeather(String response) {
        try {
            JSONObject resJson = new JSONObject(response);
            String infoCode = resJson.optString("infocode");
            if (infoCode.equals("10000")) { //string是通equals来对比两个是不是一样的。
                JSONArray liveArray = resJson.optJSONArray("lives");
                if (liveArray != null && liveArray.length() > 0) {
                    JSONObject liveJson = liveArray.optJSONObject(0);
                    String province = liveJson.optString("province");
                    String city = liveJson.optString("city");
                    String adcode = liveJson.optString("adcode");
                    String weather = liveJson.optString("weather");
                    String temperature = liveJson.optString("temperature");
                    //
                    UserCity userCity = MyApp.getLiteOrm().queryById(adcode, UserCity.class);
                    if (userCity != null) {
                        userCity.setTemp(temperature);
                        userCity.setWeather(weather);
                        MyApp.getLiteOrm().save(userCity);
                    }
                    //
                    String winddirection = liveJson.optString("winddirection");
                    String windpower = liveJson.optString("windpower");
                    String humidity = liveJson.optString("humidity");
                    String reporttime = liveJson.optString("reporttime");
                    //打印天气结果
                    String showMsg = (province + city + "，天气：" + weather + ",温度：" + temperature + "度，风向：" + winddirection + ",风速：" + windpower + ",湿度：" + humidity);
                    Log.e("zeffect", "" + showMsg);
                    //
                    cityTv.setText(city);
                    tempTv.setText(temperature + "°");
                    weatherTv.setText("天气：" + weather);
                    humTv.setText("湿度：" + humidity);
                    windDirTv.setText("风向：" + winddirection);
                    //根据天气情况修改背景
                    if (weather.equals("晴")) {
                        weatherBg.setImageResource(R.drawable.weatherbg_sunshine);
                    } else if (weather.equals("少云")
                            || weather.equals("晴间多云")
                            || weather.equals("多云")
                            || weather.equals("阴")) {
                        weatherBg.setImageResource(R.drawable.weatherbg_cloud);
                    } else if (weather.equals("有风")
                            || weather.equals("平静")
                            || weather.equals("微风")
                            || weather.equals("和风")
                            || weather.equals("清风")
                            || weather.equals("强风/劲风")
                            || weather.equals("疾风")
                            || weather.equals("大风")
                            || weather.equals("烈风")
                            || weather.equals("风暴")
                            || weather.equals("狂爆风")
                            || weather.equals("飓风")
                            || weather.equals("热带风暴")) {
                        weatherBg.setImageResource(R.drawable.weatherbg_wind);
                    } else if (weather.equals("雨")
                            || weather.equals("阵雨")
                            || weather.equals("雷阵雨")
                            || weather.equals("雷阵雨并伴有冰雹")
                            || weather.equals("小雨")
                            || weather.equals("中雨")
                            || weather.equals("大雨")
                            || weather.equals("暴雨")
                            || weather.equals("大暴雨")
                            || weather.equals("特大暴雨")
                            || weather.equals("强阵雨")
                            || weather.equals("强雷阵雨")
                            || weather.equals("极端降雨")
                            || weather.equals("毛毛雨/细雨")
                            || weather.equals("小雨-中雨")
                            || weather.equals("中雨-大雨")
                            || weather.equals("中雨-大雨")
                            || weather.equals("中雨-大雨")
                            || weather.equals("大暴雨-特大暴雨")
                            || weather.equals("雨雪天气")
                            || weather.equals("雨夹雪")
                            || weather.equals("阵雨夹雪")
                            || weather.equals("冻雨")
                    ) {
                        weatherBg.setImageResource(R.drawable.weatherbg_rain);
                    } else if (weather.equals("雪")
                            || weather.equals("阵雪")
                            || weather.equals("小雪")
                            || weather.equals("中雪")
                            || weather.equals("大雪")
                            || weather.equals("暴雪")
                            || weather.equals("小雪-中雪")
                            || weather.equals("中雪-大雪")
                            || weather.equals("大雪-暴雪")) {
                        weatherBg.setImageResource(R.drawable.weatherbg_snow);
                    } else if (weather.equals("霾")
                            || weather.equals("中度霾")
                            || weather.equals("重度霾")
                            || weather.equals("严重霾")
                            || weather.equals("浮尘")
                            || weather.equals("扬沙")
                            || weather.equals("沙尘暴")
                            || weather.equals("强沙尘暴")
                            || weather.equals("龙卷风")) {
                        weatherBg.setImageResource(R.drawable.weatherbg_haze);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
