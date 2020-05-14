package cn.zeffect.apk.teacher.studyweather.city.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import cn.zeffect.apk.teacher.studyweather.R;
import cn.zeffect.apk.teacher.studyweather.city.bean.UserCity;

public class CitysAdapter extends BaseQuickAdapter<UserCity, BaseViewHolder> {

    public CitysAdapter(List<UserCity> data) {
        super(R.layout.item_city_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, UserCity userCity) {
        baseViewHolder.setText(R.id.city_name_tv,userCity.getCityname());
        baseViewHolder.setText(R.id.item_city_weather_tv, userCity.getTemp()+"°"+userCity.getWeather());
        if (userCity.getWeather().equals("雨")) {
            baseViewHolder.setImageResource(R.id.item_bg_img,R.drawable.widget_bkg_rain);
        } else if (userCity.getWeather().equals("阴")) {
            baseViewHolder.setImageResource(R.id.item_bg_img,R.drawable.widget_bkg_overcast);
        } else if (userCity.getWeather().equals("多云")) {
            baseViewHolder.setImageResource(R.id.item_bg_img,R.drawable.widget_bkg_cloudy);
        } else if (userCity.getWeather().equals("晴")) {
            baseViewHolder.setImageResource(R.id.item_bg_img,R.drawable.widget_bkg_sunny);
        }
    }
}
