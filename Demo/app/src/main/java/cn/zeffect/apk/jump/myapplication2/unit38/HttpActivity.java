package cn.zeffect.apk.jump.myapplication2.unit38;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.zeffect.apk.jump.myapplication2.R;
import okhttp3.Call;
import okhttp3.Request;

public class HttpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputEt;
    private TextView showTv;
    private Button searchBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        //
        inputEt = findViewById(R.id.input_et);
        showTv = findViewById(R.id.show_msg);
        searchBtn = findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(this);
        findViewById(R.id.make_json_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_btn) {
            String key = inputEt.getText().toString();
            if (TextUtils.isEmpty(key)) {
                Toast.makeText(this, "请输入关键字！", Toast.LENGTH_SHORT).show();
                return;
            }
            OkHttpUtils.post()
                    .url("http://dbs.qimonjy.cn:9012//platform_intf/qimon/v5/aiquestion/queryBeiKaoKoidVideos.action")
                    .addParams("userid", "15a002dcb7b94ef499fe358b3782660e")
                    .addParams("subjectid", "5273b0ae25324f37909c72c43c8b7b3e")
                    .addParams("koid", "13")
                    .build().execute(new StringCallback() {
                @Override
                public void onBefore(Request request, int id) {
                    super.onBefore(request, id);
                    searchBtn.setEnabled(false);
                    inputEt.setEnabled(false);
                }

                @Override
                public void onAfter(int id) {
                    super.onAfter(id);
                    searchBtn.setEnabled(false);
                    inputEt.setEnabled(false);
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    showTv.setText("发生错误");
                    e.printStackTrace();
                }

                @Override
                public void onResponse(String response, int id) {
                    //没有发生错误，真正的返回了数据。
                    try {
                        JSONArray resArray = new JSONArray(response);
                        List<Video> allVideo = new ArrayList<>(resArray.length());
                        for (int i = 0; i < resArray.length(); i++) {
                            JSONObject dataJson = resArray.optJSONObject(i);
                            Video tmpVideo = new Video();
                            tmpVideo.setKoid(dataJson.optDouble("koid"));
                            tmpVideo.setKoname(dataJson.optString("koname"));
                            tmpVideo.setVideoid(dataJson.optString("videoid"));
                            tmpVideo.setVideoName(dataJson.optString("vedioname"));
                            allVideo.add(tmpVideo);
                        }
                        //
                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i < allVideo.size(); i++) {
                            Video tmpVideo = allVideo.get(i);
                            builder.append("----------------------------------------------------------\n");
                            builder.append("知识点：" + tmpVideo.getKoname() + "\n");
                            builder.append("视频名：" + tmpVideo.getVideoName() + "\n");
                        }
                        showTv.setText(builder.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


//            //GET请求
//            OkHttpUtils.get()
//                    .url("http://suggest.video.iqiyi.com/")
//                    .addParams("key", "" + key)
//                    .build().execute(new StringCallback() {
//                @Override
//                public void onBefore(Request request, int id) {
//                    super.onBefore(request, id);
//                    searchBtn.setEnabled(false);
//                    inputEt.setEnabled(false);
//                }
//
//                @Override
//                public void onAfter(int id) {
//                    super.onAfter(id);
//                    searchBtn.setEnabled(true);
//                    inputEt.setEnabled(true);
//                }
//
//                @Override
//                public void onError(Call call, Exception e, int id) {
//                    showTv.setText("发生错误！");
//                    e.printStackTrace();
//                }
//
//                @Override
//                public void onResponse(String response, int id) {
////                    showTv.setText(response);
//                    //这里是搜索的返回数据
//                    try {
//                        JSONObject resJson = new JSONObject(response);
//                        String code = resJson.optString("code");//通过OptString解析出resJson里的code数据。
//                        double count1 = resJson.optDouble("show_query_count");
//                        int count2 = resJson.optInt("show_query_count");
//                        JSONArray dataArray = resJson.optJSONArray("data");
//                        StringBuilder builder = new StringBuilder();
//                        for (int i = 0; i < dataArray.length(); i++) {
//                            JSONObject dataJson = dataArray.optJSONObject(i);
//                            String name = dataJson.optString("name");
//                            String pictureUrl = dataJson.optString("picture_url");
//                            builder.append("----------------------------------------------------\n");
//                            builder.append("名字：" + name + "\n");
//                            builder.append("图片：" + pictureUrl + "\n");
//                            if (dataJson.has("director")) {
//                                JSONArray directorArray = dataJson.optJSONArray("director");
//                                if (directorArray != null) {
//                                    builder.append("导演：");
//                                    for (int j = 0; j < directorArray.length(); j++) {
//                                        String director = directorArray.optString(j);
//                                        builder.append("" + director + ",");
//                                    }
//                                    builder.append("\n");
//                                }
//                            }
//                            if (dataJson.has("main_actor")) {
//                                builder.append("主演：");
//                                JSONArray main_actorArray = dataJson.optJSONArray("main_actor");
//                                for (int j = 0; j < main_actorArray.length(); j++) {
//                                    String main_actor = main_actorArray.optString(j);
//                                    builder.append("" + main_actor + ",");
//                                }
//                                builder.append("\n");
//                            }
//
//                            //
//                        }
//                        showTv.setText(builder.toString());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void inProgress(float progress, long total, int id) {
//                    super.inProgress(progress, total, id);
//                }
//            });

        } else if (v.getId() == R.id.make_json_btn) {
            //组装JSON数据
            JSONObject resJson = new JSONObject();
            try {
                resJson.put("code", "A0000");
                resJson.put("show_query_count", 20);
                //
                JSONObject attrJson = new JSONObject();
                attrJson.put("event_id", "f31dd4b16b48a62897d2614b749fd6b8");
                attrJson.put("bkt", "suggest_8#f31dd4b16b48a62897d2614b749fd6b8#0");
                resJson.put("attributes", attrJson);

                JSONArray dataArray = new JSONArray();
                //武林外传
                JSONObject tmpJson1 = new JSONObject();
                tmpJson1.put("aid", 105990);
                tmpJson1.put("name", "武林外传");
                tmpJson1.put("link", "http://www.iqiyi.com/v_19rrjbfmso.html");
                tmpJson1.put("picture_url", "http://pic4.iqiyipic.com/image/20190808/7a/23/v_50077017_m_601_m6.jpg");
                tmpJson1.put("cid", 1);
                tmpJson1.put("cname", "电影");
                JSONArray dict1Array = new JSONArray();
                dict1Array.put("尚敬");
                tmpJson1.put("director", dict1Array);
                JSONArray mainArray = new JSONArray();
                mainArray.put("闫妮");
                mainArray.put("姚晨");
                mainArray.put("沙溢");
                mainArray.put("喻恩泰");
                mainArray.put("倪虹洁");
                mainArray.put("姜超");
                mainArray.put("肖剑");
                mainArray.put("范明");
                mainArray.put("午马");
                mainArray.put("岳跃利");
                mainArray.put("王磊");
                tmpJson1.put("main_actor", mainArray);
                tmpJson1.put("is_purchase", 0);
                tmpJson1.put("region", "华语");
                tmpJson1.put("year", 2011);
                tmpJson1.put("duration", 5700);
                tmpJson1.put("vid", 77017);
                JSONArray linkArray = new JSONArray();
                linkArray.put("http://www.iqiyi.com/v_19rrjbfmso.html");
                tmpJson1.put("link_address", linkArray);
                tmpJson1.put("normalize_query", "武林外传");
                tmpJson1.put("final_score", 1.93718);
                dataArray.put(tmpJson1);
                //武林风
                JSONObject tmp2 = new JSONObject();
                tmp2.put("aid", 0);
                tmp2.put("name", "武林风");
                tmp2.put("link", "");
                tmp2.put("picture_url", "");
                tmp2.put("cid", 0);
                tmp2.put("cname", "");
                tmp2.put("is_purchase", 0);
                tmp2.put("region", "");
                tmp2.put("year", 2010);
                tmp2.put("duration", 0);
                tmp2.put("vid", 0);
                tmp2.put("normalize_query", "武林风");
                tmp2.put("final_score", 1.44475);
                tmp2.put("is_album_log", true);
                dataArray.put(tmp2);
                //
                resJson.put("data", dataArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            showTv.setText(resJson.toString());
        }
    }
}
