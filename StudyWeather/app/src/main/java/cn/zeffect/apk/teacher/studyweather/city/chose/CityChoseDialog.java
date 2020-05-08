package cn.zeffect.apk.teacher.studyweather.city.chose;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import cn.zeffect.apk.teacher.studyweather.MyApp;
import cn.zeffect.apk.teacher.studyweather.R;
import cn.zeffect.apk.teacher.studyweather.city.bean.CityModel;

public class CityChoseDialog extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_chose_city_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //第一次打开，选择省市县的界面的时候，要看一下本地有没有省市县的数据，如果没有，要从网络上获取，并且保存下来。
        //1. 第一个问题？ 如何判断本地有没有数据？
        long count = MyApp.getLiteOrm().queryCount(CityModel.class);
        if (count <= 0) { // 肯定没有数据
            //下载数据
            Toast.makeText(getContext(), "下载数据！！" , Toast.LENGTH_SHORT).show();
        }
    }
}
