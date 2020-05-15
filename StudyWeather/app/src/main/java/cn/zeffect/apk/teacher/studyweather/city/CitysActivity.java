package cn.zeffect.apk.teacher.studyweather.city;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.zeffect.apk.teacher.studyweather.MyApp;
import cn.zeffect.apk.teacher.studyweather.R;
import cn.zeffect.apk.teacher.studyweather.city.adapter.CityAdapter;
import cn.zeffect.apk.teacher.studyweather.city.adapter.CitysAdapter;
import cn.zeffect.apk.teacher.studyweather.city.bean.UserCity;
import cn.zeffect.apk.teacher.studyweather.city.chose.CityChoseDialog;
import cn.zeffect.apk.teacher.studyweather.utils.async.Async;
import okhttp3.Response;

public class CitysActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener, OnItemLongClickListener, CityAdapter.AdapterListener {

    private RecyclerView cityRecy;
    List<UserCity> cityList;
    private CitysAdapter adapter;

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
        cityList = MyApp.getLiteOrm().query(UserCity.class);
        adapter = new CitysAdapter(cityList);
        cityRecy.setAdapter(adapter);
        //3. 可以优化
        cityRecy.setHasFixedSize(true);
        //ITEM的事件监听
//        adapter.setAdapterListener(this);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
    }

    private ArrayList<String> addCitys = new ArrayList<>();

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_city_img) {
            new CityChoseDialog()
                    .setAddListener(new CityChoseDialog.AddListener() {
                        @Override
                        public void add(String adcode, String cityname) {
                            //添加城市
                            new Async<String, Void, UserCity>(CitysActivity.this) {
                                @Override
                                protected void onPreExecute(Context pTarget) throws Exception {
                                    super.onPreExecute(pTarget);
                                    showDialog("正在加载天气……");
                                }

                                @Override
                                protected UserCity doInBackground(Context pTarget, String... params) throws Exception {

                                    String adcode = params[0];
                                    String cityname = params[1];
                                    //
                                    UserCity userCity = new UserCity();
                                    userCity.setAdcode(adcode);
                                    userCity.setCityname(cityname);
                                    userCity.setType(UserCity.TYPE_USER_ADD);
                                    //获取天气
                                    Response response = OkHttpUtils.get()
                                            .tag("weather")
                                            .url("https://restapi.amap.com/v3/weather/weatherInfo")
                                            .addParams("key", "9d930a1bfb0b3b59568ad9b43399ac3b")
                                            .addParams("city", "" + adcode)
                                            .addParams("extensions", "base")
                                            .build().execute();
                                    String info = "";
                                    if (response.isSuccessful()) {
                                        info = response.body().string();
                                    }
                                    response.body().close();
                                    //
                                    JSONObject resJson = new JSONObject(info);
                                    String infoCode = resJson.optString("infocode");
                                    if (infoCode.equals("10000")) { //string是通equals来对比两个是不是一样的。
                                        JSONArray liveArray = resJson.optJSONArray("lives");
                                        if (liveArray != null && liveArray.length() > 0) {
                                            JSONObject liveJson = liveArray.optJSONObject(0);
                                            String weather = liveJson.optString("weather");
                                            String temperature = liveJson.optString("temperature");
                                            userCity.setWeather(weather);
                                            userCity.setTemp(temperature);
                                            //如果想要存到数据库
                                            MyApp.getLiteOrm().save(userCity);
                                        }
                                    }
                                    //
                                    return userCity;
                                }

                                @Override
                                protected void onPostExecute(Context pTarget, UserCity pResult) throws Exception {
                                    super.onPostExecute(pTarget, pResult);
                                    if (pResult != null) {
                                        cityList.add(pResult);
                                        addCitys.add(pResult.getAdcode());
                                        adapter.notifyItemInserted(cityList.size() - 1);
                                    }
                                }
                            }.execute(adcode, cityname);
                        }
                    })
                    .show(getSupportFragmentManager(), CityChoseDialog.class.getName());
        } else if (v.getId() == R.id.back_btn) {
            Intent intent = new Intent();
            if (delCitys.size() > 0) {
                intent.putStringArrayListExtra("del_citys", delCitys);
            }
            if (addCitys.size() > 0) {
                intent.putStringArrayListExtra("add_citys", addCitys);
            }
            setResult(Activity.RESULT_OK, intent);
            this.finish();
        }
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

    }

    @Override
    public boolean onItemLongClick(@NonNull final BaseQuickAdapter adapter, @NonNull View view, final int position) {
        final UserCity userCity = cityList.get(position);
        if (userCity.getType().equals(UserCity.TYPE_LOCATION)) {//如果是定位城市，不允许删除。
            return false;
        }
        SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText("删除");
        dialog.setContentText("是否需要删除" + userCity.getCityname() + "吗?");
        dialog.setConfirmText("删除");
        dialog.setCancelText("取消");
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(final SweetAlertDialog sweetAlertDialog) {
                //删除一个城市
                MyApp.getLiteOrm().delete(userCity);
                cityList.remove(position);
                adapter.notifyItemRemoved(position);
                delCitys.add(userCity.getAdcode());
                sweetAlertDialog.dismiss();
            }
        });
        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
        dialog.show();
        return true;
    }

    private ArrayList<String> delCitys = new ArrayList<>();

    @Override
    public void onLongClick(final int position) {
        final UserCity userCity = cityList.get(position);
        if (userCity.getType().equals(UserCity.TYPE_LOCATION)) {//如果是定位城市，不允许删除。
            return;
        }
        SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText("删除");
        dialog.setContentText("是否需要删除" + userCity.getCityname() + "吗?");
        dialog.setConfirmText("删除");
        dialog.setCancelText("取消");
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(final SweetAlertDialog sweetAlertDialog) {
                //删除一个城市
                MyApp.getLiteOrm().delete(userCity);
                sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                sweetAlertDialog.setConfirmText("");
                sweetAlertDialog.showCancelButton(false);
                sweetAlertDialog.setConfirmClickListener(null);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cityList.remove(position);
                        adapter.notifyItemRemoved(position);
                        sweetAlertDialog.dismiss();
                    }
                }, 1000);
                delCitys.add(userCity.getAdcode());
            }
        });
        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
        dialog.show();
    }
}
