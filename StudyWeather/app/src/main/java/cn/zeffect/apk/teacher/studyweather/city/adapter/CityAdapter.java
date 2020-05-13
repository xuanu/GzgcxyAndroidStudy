package cn.zeffect.apk.teacher.studyweather.city.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.zeffect.apk.teacher.studyweather.R;
import cn.zeffect.apk.teacher.studyweather.city.bean.UserCity;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder> {

    private List<UserCity> cities; //用来存入所有的城市

    public CityAdapter(List<UserCity> tmpCitys) {
        this.cities = tmpCitys;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tmpView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_layout, parent, false);
        return new MyViewHolder(tmpView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserCity city = cities.get(position);
        holder.cityTv.setText(city.getCityname());
        holder.weatherTv.setText(city.getTemp() + "°" + city.getWeather());
        //接下来是背景图的变化了
        if (city.getWeather().equals("雨")) {
            holder.itemBgImg.setImageResource(R.drawable.widget_bkg_rain);
        } else if (city.getWeather().equals("阴")) {
            holder.itemBgImg.setImageResource(R.drawable.widget_bkg_overcast);
        } else if (city.getWeather().equals("多云")) {
            holder.itemBgImg.setImageResource(R.drawable.widget_bkg_cloudy);
        } else if (city.getWeather().equals("晴")) {
            holder.itemBgImg.setImageResource(R.drawable.widget_bkg_sunny);
        }
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView cityTv, weatherTv;
        private ImageView itemBgImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cityTv = itemView.findViewById(R.id.city_name_tv);
            weatherTv = itemView.findViewById(R.id.item_city_weather_tv);
            itemBgImg = itemView.findViewById(R.id.item_bg_img);
        }
    }
}
