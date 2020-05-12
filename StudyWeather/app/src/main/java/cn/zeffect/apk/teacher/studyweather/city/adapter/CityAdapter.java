package cn.zeffect.apk.teacher.studyweather.city.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserCity city = cities.get(position);
        holder.cityTv.setText(city.getCityname());
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView cityTv, weatherTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cityTv = itemView.findViewById(R.id.city_name_tv);
            weatherTv = itemView.findViewById(R.id.item_city_weather_tv);
        }
    }
}
