package cn.zeffect.apk.teacher.studyweather.city.chose;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import cn.zeffect.apk.teacher.studyweather.MyApp;
import cn.zeffect.apk.teacher.studyweather.R;
import cn.zeffect.apk.teacher.studyweather.city.bean.CityModel;
import okhttp3.Call;
import okhttp3.Response;

public class CityChoseDialog extends DialogFragment {

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_chose_city_fragment, container, false);
    }

    private Spinner provinceSp;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(STYLE_NO_TITLE);

        provinceSp = view.findViewById(R.id.province_sp);


        new AsyncTask<Void, Void, List<String>>() {
            private ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //创建一个等待框，用来显示 正在加载中……
                dialog = new ProgressDialog(getContext());
                dialog.setMessage("正在加载数据……");
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }

            @Override
            protected List<String> doInBackground(Void... voids) {
                //第一次打开，选择省市县的界面的时候，要看一下本地有没有省市县的数据，如果没有，要从网络上获取，并且保存下来。
                //1. 第一个问题？ 如何判断本地有没有数据？
                long count = MyApp.getLiteOrm().queryCount(CityModel.class);
                if (count <= 0) { // 肯定没有数据
                    //.下载数据
                    //1. 异步的优点为： 不会卡线程
                    try {
                        Response response = OkHttpUtils.get()
                                .url("https://restapi.amap.com/v3/config/district")
                                .addParams("key", "9d930a1bfb0b3b59568ad9b43399ac3b")
                                .addParams("keywords", "")
                                .addParams("subdistrict", "3")
                                .addParams("extensions", "base")
                                .build().execute();
                        String info = "";
                        if (response.isSuccessful()) {
                            info = response.body().string();
                        }
                        response.body().close();//关闭数据流
                        // 数据返回的就是Info这个字符串
                        JSONObject infoJson = new JSONObject(info);
                        String infocode = infoJson.optString("infocode");
                        if (infocode.equals("10000")) {
                            JSONArray districtArray = infoJson.optJSONArray("districts");
                            JSONObject chinaJson = districtArray.optJSONObject(0);
                            JSONArray chinaArray = chinaJson.optJSONArray("districts");
                            for (int i = 0; i < chinaArray.length(); i++) {
                                JSONObject provinceJson = chinaArray.optJSONObject(i);
                                String province = provinceJson.optString("name");
                                JSONArray cityArray = provinceJson.optJSONArray("districts");
                                for (int j = 0; j < cityArray.length(); j++) {
                                    JSONObject cityJson = cityArray.optJSONObject(j);
                                    String cityName = cityJson.optString("name");
                                    JSONArray countyArray = cityJson.optJSONArray("districts");
                                    for (int k = 0; k < countyArray.length(); k++) {
                                        JSONObject countyJson = countyArray.optJSONObject(k);
                                        String countyName = countyJson.optString("name");
                                        String cityCode = countyJson.optString("citycode");
                                        String adcode = countyJson.optString("adcode");
                                        CityModel cityModel = new CityModel();
                                        cityModel.setProvince(province);
                                        cityModel.setCity(cityName);
                                        cityModel.setDistrict(countyName);
                                        cityModel.setCityCode(cityCode);
                                        cityModel.setAdCode(adcode);
                                        MyApp.getLiteOrm().save(cityModel);
                                    }
                                }
                            }
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
                List<CityModel> cityModels = MyApp.getLiteOrm().query(CityModel.class);
                List<String> provinceList = new ArrayList<>(); //存放所有的省
                for (int i = 0; i < cityModels.size(); i++) {
                    CityModel cityModel = cityModels.get(i);
                    String province = cityModel.getProvince();
                    if (!provinceList.contains(province)) {
                        provinceList.add(province);
                    }
                }
                return provinceList;
            }

            @Override
            protected void onPostExecute(List<String> aVoid) {
                super.onPostExecute(aVoid);
                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, aVoid);
                provinceSp.setAdapter(adapter);
                if (dialog != null) {
                    dialog.cancel();
                }
            }
        }.execute();
    }
}
