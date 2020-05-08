package cn.zeffect.apk.jump.myapplication2.unit39;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.zeffect.apk.jump.myapplication2.R;

public class DialogActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        findViewById(R.id.show_normal_btn).setOnClickListener(this);
        findViewById(R.id.show_3_btn).setOnClickListener(this);
        findViewById(R.id.show_circle_btn).setOnClickListener(this);
        findViewById(R.id.show_progress_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.show_normal_btn) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            new TwoButtonDialog().show(getSupportFragmentManager(), TwoButtonDialog.class.getName());
        } else if (v.getId() == R.id.show_3_btn) {
            new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                    .setContentText("正在下载数据……")
                    .setTitleText("")
                    .show();
        } else if (v.getId() == R.id.show_circle_btn) {
            new SweetAlertDialog(this,SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("删除")
                    .setContentText("真的要删除这个好友吗？")
                    .setConfirmText("确定")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            Toast.makeText(DialogActivity.this, "删除了", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setCancelText("不删除")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            Toast.makeText(DialogActivity.this, "不删除", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();
        } else if (v.getId() == R.id.show_progress_btn) {

        }
    }
}
