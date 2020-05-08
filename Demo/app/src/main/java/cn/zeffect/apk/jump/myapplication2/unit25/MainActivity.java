package cn.zeffect.apk.jump.myapplication2.unit25;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import cn.zeffect.apk.jump.myapplication2.R;
import cn.zeffect.apk.jump.myapplication2.unit26.TestService;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_recycler_view_layout);
        //当MainActivity启动时，启动Service
        Intent serviceIntent = new Intent(this, TestService.class);
        startService(serviceIntent);
        //
        pager = findViewById(R.id.view_pager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TestFragment());
        fragments.add(new Test2Fragment());
        fragments.add(new TestFragment());
        fragments.add(new Test2Fragment());
        fragments.add(new TestFragment());
        fragments.add(new Test2Fragment());
        TestAdapter adapter = new TestAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {

    }
}
