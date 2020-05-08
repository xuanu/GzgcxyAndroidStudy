package cn.zeffect.apk.jump.myapplication2.unit33;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.zeffect.apk.jump.myapplication2.R;
import okhttp3.Call;
import okhttp3.Request;

public class ProgressActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar bar;
    private Button downloadBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        //
        bar = findViewById(R.id.h_pb);
        downloadBtn = findViewById(R.id.start_download_btn);
        downloadBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == downloadBtn) {

//            OkHttpUtils.post()
//                    .addFile("","",null)
//                    .url("")
//                    .addParams("","")
//                    .build().execute();

            //点击开始下载之后，不允许重复点击 ，要怎么做？
            File cacheFile = new File(getExternalCacheDir(), "tmp.file");
            OkHttpUtils
                    .get()
                    .url("http://upload.qimonjy.cn:7777//intf/files/update/app/live/qimonlive308.apk")
                    .build()
                    .execute(new FileCallBack(cacheFile.getParent(), cacheFile.getName()) {

                        @Override
                        public void onBefore(Request request, int id) {
                            super.onBefore(request, id);
                            downloadBtn.setEnabled(false);
                        }

                        @Override
                        public void onAfter(int id) {
                            super.onAfter(id);
                            downloadBtn.setEnabled(true);
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(File response, int id) {

                        }

                        @Override
                        public void inProgress(float progress, long total, int id) {
                            super.inProgress(progress, total, id);
                            bar.setProgress((int) (100 * progress));
                        }
                    });
        }
    }
}
