package cn.zeffect.apk.jump.myapplication2.unit35;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.zeffect.apk.jump.myapplication2.R;

public class SpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userEt, pwEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        //
        userEt = findViewById(R.id.user_et);
        pwEt = findViewById(R.id.pw_et);
        findViewById(R.id.login_btn).setOnClickListener(this);
        findViewById(R.id.save_btn).setOnClickListener(this);
        //
        String user = SpUtils.get(this, "sp_user_name", "");
        String pw = SpUtils.get(this, "sp_user_pw", "");
        if (!TextUtils.isEmpty(user)) {
            userEt.setText(user);
        }
        if (!TextUtils.isEmpty(pw)) {
            pwEt.setText(pw);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_btn) {
            String user = userEt.getText().toString();
            String pw = pwEt.getText().toString();
            if (TextUtils.isEmpty(user)) {
                Toast.makeText(this, "请输入用户名！！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(pw)) {
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }
            //
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            //
            SpUtils.put(this, "sp_user_name", user);//保存用户名到sp文件内
            SpUtils.put(this, "sp_user_pw", pw); // 保存密码
            //
        } else if (v.getId() == R.id.save_btn) {
//            SpUtils.put(this, "key1", 10);
            SpUtils.put(this, "key2", 3.1415926f);
//            SpUtils.put(this, "key3", "这是String类型");
//            SpUtils.put(this, "is_login", true);
            //
            Log.e("zeffect", "" + SpUtils.get(this, "key2", 0f));
        }
    }
}
