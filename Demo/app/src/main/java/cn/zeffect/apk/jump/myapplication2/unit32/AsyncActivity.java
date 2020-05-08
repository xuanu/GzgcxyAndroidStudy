package cn.zeffect.apk.jump.myapplication2.unit32;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.zeffect.apk.jump.myapplication2.R;

public class AsyncActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView showTv;

    private Handler msgHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {//处理消息
            super.handleMessage(msg);
            //msg为传递过来的消息
            int what = msg.what;
            if (what == 1001) {
                show("发送了一个空的消息！！" + System.currentTimeMillis());
            } else if (what == 1002) {
                String tmpStr = (String) msg.obj;
                show("自定义：" + tmpStr);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);
        //
        showTv = findViewById(R.id.show_tv);
        findViewById(R.id.start_thread_btn).setOnClickListener(this);
        findViewById(R.id.start_async_btn).setOnClickListener(this);
    }

    private void show(String msg) {
        if (msg == null) {
            return;
        }
        showTv.setText(showTv.getText() + "\n" + msg);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_thread_btn) {
            new Thread(myRun).start();
        } else if (v.getId() == R.id.start_async_btn) {
            //AsyncTask的使用
            new AsyncTask<String, Void, String>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(String... voids) {//后台工作是一定要实现的。
                    Log.e("zeffect", "" + voids[0]);
                    Log.e("zeffect", "" + voids[1]);
                    return "你好，我是返回值";
                }

                @Override
                protected void onProgressUpdate(Void... values) {
                    super.onProgressUpdate(values);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    Log.e("zeffect", "返回值为：" + s);
                }
            }.execute("1", "Hello!");
        }
    }

    private Runnable myRun = new Runnable() {
        @Override
        public void run() {
            Log.e("zeffect", "MYRUN子线程被调用！！！");
            //在这个地方，想要操作View的属性，要怎么办？

            msgHandler.sendEmptyMessage(1001);
            msgHandler.sendEmptyMessageDelayed(1001, 3000);
            //
            Message tmpMsg = msgHandler.obtainMessage();
            tmpMsg.what = 1002;
            tmpMsg.obj = "这是自定义消息！";
            msgHandler.sendMessage(tmpMsg);
            //
            Message tmpMsg2 = msgHandler.obtainMessage();
            tmpMsg2.what = 1002;
            tmpMsg2.obj = "这是自定义消息！";
            msgHandler.sendMessageDelayed(tmpMsg2, 3000);


            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
