package cn.zeffect.apk.teacher.studyweather.city.chose;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.litesuits.orm.db.assit.QueryBuilder;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.zeffect.apk.teacher.studyweather.MyApp;
import cn.zeffect.apk.teacher.studyweather.R;
import cn.zeffect.apk.teacher.studyweather.city.bean.CityModel;
import cn.zeffect.apk.teacher.studyweather.city.bean.UserCity;
import okhttp3.Response;

public class CityChoseDialog extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

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
    private Spinner citySp, countySp;
    private List<CityModel> provinceModels; //用来存放所有的省市县
    private List<CityModel> mCityModels;    //所有市的
    private List<CityModel> mCountyModel;
    private List<String> provinceList;
    private List<String> cityList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(STYLE_NO_TITLE);

        provinceSp = view.findViewById(R.id.province_sp);
        citySp = view.findViewById(R.id.city_sp);
        countySp = view.findViewById(R.id.county_sp);
        provinceSp.setOnItemSelectedListener(this);
        citySp.setOnItemSelectedListener(this);
        countySp.setOnItemSelectedListener(this);
        view.findViewById(R.id.sure_btn).setOnClickListener(this);
        //
        new AsyncTask<Void, Void, List<String>>() {
            private SweetAlertDialog dialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //创建一个等待框，用来显示 正在加载中……
//                dialog = new ProgressDialog(getContext());
//                dialog.setMessage("正在加载数据……");
//                dialog.setCancelable(false);
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.show();
                dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
                dialog.setContentText("正在加载……");
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
                                //
                                CityModel provinceModel = new CityModel();
                                provinceModel.setCityCode("");
                                provinceModel.setAdCode(provinceJson.optString("adcode"));
                                provinceModel.setName(provinceJson.optString("name"));
                                provinceModel.setCenterPoint(provinceJson.optString("center"));
                                provinceModel.setLevel(provinceJson.optString("level"));

                                ArrayList<CityModel> cityModels = new ArrayList<>(cityArray.length());
                                //
                                for (int j = 0; j < cityArray.length(); j++) {
                                    JSONObject cityJson = cityArray.optJSONObject(j);
                                    String cityName = cityJson.optString("name");
                                    JSONArray countyArray = cityJson.optJSONArray("districts");
                                    //
                                    CityModel cityModel = new CityModel();
                                    cityModel.setCityCode(cityJson.optString("citycode"));
                                    cityModel.setAdCode(cityJson.optString("adcode"));
                                    cityModel.setName(cityJson.optString("name"));
                                    cityModel.setCenterPoint(cityJson.optString("center"));
                                    cityModel.setLevel(cityJson.optString("level"));

                                    ArrayList<CityModel> countyModels = new ArrayList<>();
                                    for (int k = 0; k < countyArray.length(); k++) {
                                        JSONObject countyJson = countyArray.optJSONObject(k);
                                        String countyName = countyJson.optString("name");
                                        String cityCode = countyJson.optString("citycode");
                                        String adcode = countyJson.optString("adcode");
                                        CityModel countyModel = new CityModel();
                                        countyModel.setCityCode(countyJson.optString("citycode"));
                                        countyModel.setAdCode(countyJson.optString("adcode"));
                                        countyModel.setName(countyJson.optString("name"));
                                        countyModel.setCenterPoint(countyJson.optString("center"));
                                        countyModel.setLevel(countyJson.optString("level"));
                                        countyModels.add(countyModel);
                                    }
                                    cityModel.setDistricts(countyModels);
                                    cityModels.add(cityModel);
                                }
                                provinceModel.setDistricts(cityModels);
                                MyApp.getLiteOrm().save(provinceModel);//存入数据
                            }
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
                List<CityModel> cityModels = MyApp.getLiteOrm().query(CityModel.class);
                provinceModels = cityModels;
                List<String> provinceList = new ArrayList<>(); //存放所有的省
                for (int i = 0; i < cityModels.size(); i++) {
                    CityModel cityModel = cityModels.get(i);
                    String province = cityModel.getName();
                    provinceList.add(province);
                }
                return provinceList;
            }

            @Override
            protected void onPostExecute(List<String> aVoid) {
                super.onPostExecute(aVoid);
                provinceList = aVoid;
                ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.item_spinner_item, aVoid);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                provinceSp.setAdapter(adapter);
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        }.execute();
    }

    /**
     * 用来存储当前选择了哪个省
     */
    private CityModel selectProvince;
    /**
     * 用来存储当前选择了哪个城市
     */
    private CityModel selectCity;

    private CityModel selectCounty; // 选择了哪个县

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == provinceSp) {
            selectProvince = provinceModels.get(position);
            loadCity(selectProvince);
        } else if (parent == citySp) {
            selectCity = mCityModels.get(position);
            loadCounty(selectCity);
        } else if (parent == countySp) {
            selectCounty = mCountyModel.get(position);//这样我就知道用户选择了哪个城市
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //什么都没做
    }


    private void loadCounty(final CityModel city) {
        if (city == null) {
            return;
        }
        new AsyncTask<String, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(String... strings) {
                List<CityModel> cityModels = city.getDistricts();
                mCountyModel = cityModels;
                List<String> countyList = new ArrayList<>(cityModels.size());
                for (int i = 0; i < cityModels.size(); i++) {
                    CityModel cityModel = cityModels.get(i);
                    countyList.add(cityModel.getName());
                }
                return countyList;
            }

            @Override
            protected void onPostExecute(List<String> cityModels) {
                super.onPostExecute(cityModels);
                ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.item_spinner_item, cityModels);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                countySp.setAdapter(adapter);
            }
        }.execute();

    }

    /**
     * 通过选择的省份加载城市
     *
     * @param province 某个省
     */
    private void loadCity(final CityModel province) {
        if (province == null) {
            return;
        }
        new AsyncTask<String, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(String... voids) {
                List<CityModel> cityModels = province.getDistricts();
                mCityModels = cityModels;
                List<String> cityList = new ArrayList<>();
                for (int i = 0; i < cityModels.size(); i++) {
                    CityModel cityModel = cityModels.get(i);
                    String cityName = cityModel.getName();
                    cityList.add(cityName);
                }
                return cityList;
            }

            @Override
            protected void onPostExecute(List<String> strings) {
                super.onPostExecute(strings);
                cityList = strings;
                ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.item_spinner_item, cityList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                citySp.setAdapter(adapter);
            }
        }.execute();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sure_btn) {
            //手动选择城市之后，保存到数据。
            if (selectCounty == null) {
                return;
            }
            String adcode = selectCounty.getAdCode();
            String cityName = selectCounty.getName();
            UserCity userCity = new UserCity();
            userCity.setCityname(cityName);
            userCity.setAdcode(adcode);
            userCity.setType(UserCity.TYPE_USER_ADD);
            MyApp.getLiteOrm().save(userCity);
            //
            CityChoseDialog.this.dismiss();
        }
    }
}
