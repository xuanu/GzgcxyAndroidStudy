package cn.zeffect.apk.jump.myapplication2.unit25;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import cn.zeffect.apk.jump.myapplication2.R;
import cn.zeffect.apk.jump.myapplication2.unit252.Fragment1;
import cn.zeffect.apk.jump.myapplication2.unit252.Fragment2;
import cn.zeffect.apk.jump.myapplication2.unit252.Fragment3;
import cn.zeffect.apk.jump.myapplication2.unit252.Fragment4;

public class PagerActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private ViewPager pager;
    private RadioButton btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        //
        pager = findViewById(R.id.view_pager);
        btn1 = findViewById(R.id.btn_wx);
        btn2 = findViewById(R.id.btn_txl);
        btn3 = findViewById(R.id.btn_find);
        btn4 = findViewById(R.id.btn_my);
        //事件也要进行修改，我们现在不是点击效果，而是选中效果。
//        btn1.setOnClickListener(this); // 快捷键ALT+ENTER
//        btn2.setOnClickListener(this);
//        btn3.setOnClickListener(this);
//        btn4.setOnClickListener(this);
        btn1.setOnCheckedChangeListener(this);
        btn2.setOnCheckedChangeListener(this);
        btn3.setOnCheckedChangeListener(this);
        btn4.setOnCheckedChangeListener(this);

        //
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new Fragment1());
        fragments.add(new Fragment1());//ALT+ENTER
        fragments.add(new Fragment1());
        fragments.add(new Fragment1());
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);//创建一个ViewPager
        //产生关联
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(this);

    }

    @Override
    public void onClick(View v) {
//        if (v == btn1) {
//            pager.setCurrentItem(0);
//        } else if (v == btn2) {
//            pager.setCurrentItem(1);
//        } else if (v == btn3) {
//            pager.setCurrentItem(2);
//        } else if (v == btn4) {
//            pager.setCurrentItem(3);
//        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //选中变化事件监听
        if (isChecked) {
            if (buttonView == btn1) {
                pager.setCurrentItem(0);
            } else if (buttonView == btn2) {
                pager.setCurrentItem(1);
            } else if (buttonView == btn3) {
                pager.setCurrentItem(2);
            } else if (buttonView == btn4) {
                pager.setCurrentItem(3);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        Log.e("zeffect", "onPageScrolled");
    }

    @Override
    public void onPageSelected(int position) {//告诉开发者，当前滑动到了第几个
        Log.e("zeffect", "onPageSelected:" + position);
        if (position == 0) {
            btn1.setChecked(true);
        } else if (position == 1) {
            btn2.setChecked(true);
        } else if (position == 2) {
            btn3.setChecked(true);
        } else if (position == 3) {
            btn4.setChecked(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        Log.e("zeffect", "onPageScrollStateChanged");
    }

    //创建Adapter的步骤
    public class MyPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> showFragments; // 需要显示的Fragments

        public MyPagerAdapter(FragmentManager fm, List<Fragment> tmps) {
            super(fm);
            this.showFragments = tmps;
        }//补全可以使用快捷键，ALT+ENTER

        @Override
        public Fragment getItem(int position) {
            return showFragments.get(position);
        }

        @Override
        public int getCount() {
            return showFragments.size();
        }

    }
}
