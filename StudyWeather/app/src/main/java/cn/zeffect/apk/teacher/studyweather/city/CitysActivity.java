package cn.zeffect.apk.teacher.studyweather.city;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;

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

public class CitysActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener, OnItemLongClickListener, CityAdapter.AdapterListener {

    private RecyclerView cityRecy;
    List<UserCity> cityList;
    private CityAdapter adapter;

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
        adapter = new CityAdapter(cityList);
        cityRecy.setAdapter(adapter);
        //3. 可以优化
        cityRecy.setHasFixedSize(true);
        //ITEM的事件监听
        adapter.setAdapterListener(this);
//        adapter.setOnItemClickListener(this);
//        adapter.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_city_img) {
            new CityChoseDialog()
                    .setAddListener(new CityChoseDialog.AddListener() {
                        @Override
                        public void add(String adcode, String cityname) {
                            UserCity userCity = new UserCity();
                            userCity.setAdcode(adcode);
                            userCity.setCityname(cityname);
                            userCity.setType(UserCity.TYPE_USER_ADD);
                            cityList.add(userCity);
                            adapter.notifyItemInserted(cityList.size() - 1);
                        }
                    })
                    .show(getSupportFragmentManager(), CityChoseDialog.class.getName());
        } else if (v.getId() == R.id.back_btn) {
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
