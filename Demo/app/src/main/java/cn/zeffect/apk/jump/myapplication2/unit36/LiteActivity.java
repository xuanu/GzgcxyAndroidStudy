package cn.zeffect.apk.jump.myapplication2.unit36;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.zeffect.apk.jump.myapplication2.MyApp;
import cn.zeffect.apk.jump.myapplication2.R;

public class LiteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userEt, phoneEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lite);
        userEt = findViewById(R.id.user_et);
        phoneEt = findViewById(R.id.pw_et);
        findViewById(R.id.login_btn).setOnClickListener(this);
        findViewById(R.id.insert_btn).setOnClickListener(this);
        findViewById(R.id.update_btn).setOnClickListener(this);
        findViewById(R.id.query_btn).setOnClickListener(this);
        findViewById(R.id.del_btn).setOnClickListener(this);
        //快捷键复制：鼠标移动到需要复制的行，按CTRL+D

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_btn) {
            String user = userEt.getText().toString();
            String pw = phoneEt.getText().toString();
            if (TextUtils.isEmpty(user)) {
                Toast.makeText(this, "请输入用户名！！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(pw)) {
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }
            //实现保存一个用户到数据库
            User user1 = new User();
            user1.setPhone(pw);
            user1.setUserName(user);
            MyApp.liteOrm.save(user1);
        } else if (v.getId() == R.id.insert_btn) {
            User user2 = new User();
            user2.setUserName("郑治玄1");
            user2.setPhone("没有电话");
            MyApp.liteOrm.insert(user2);
        } else if (v.getId() == R.id.update_btn) {
            User user2 = new User();
            user2.setId(1);
            user2.setUserName("郑治玄1");
            user2.setPhone("没有电话");
            MyApp.liteOrm.update(user2);
            //更新是根据主键来进行更新。
        } else if (v.getId() == R.id.query_btn) {
            //查询
            MyApp.liteOrm.query(User.class); // 查询User.class所在表的所有
            QueryBuilder builder = new QueryBuilder(User.class)
                    .whereEquals(User.COL_PHONE, "12345678910")
                    .whereAppendAnd()
                    .whereEquals(User.COL_USER_NAME, "郑治玄");
            MyApp.liteOrm.query(builder);

//            List<User> datas =
//            for (int i = 0; i < datas.size(); i++) {
//                Log.e("zeffect", "用户名：" + datas.get(i).getUserName() + ",电话：" + datas.get(i).getPhone());
//            }
            User my = MyApp.liteOrm.queryById("1", User.class);
            if (my != null) {
                Log.e("zeffect", "用户名：" + my.getUserName() + ",电话：" + my.getPhone());
            }
            MyApp.liteOrm.queryCount(User.class);//查询表数据总数
            QueryBuilder builder1 = new QueryBuilder(User.class)
                    .whereEquals(User.COL_PHONE, "12345678910")
                    .whereAppendAnd()
                    .whereEquals(User.COL_USER_NAME, "郑治玄");
            //查询用户名为郑治玄并且电话为12345678910的数据有几条？
            long count = MyApp.liteOrm.queryCount(builder1);
            Log.e("zeffect", "user表有：" + count + "条数据！");
        } else if (v.getId() == R.id.del_btn) {
            MyApp.liteOrm.deleteDatabase();
        }
    }
}
