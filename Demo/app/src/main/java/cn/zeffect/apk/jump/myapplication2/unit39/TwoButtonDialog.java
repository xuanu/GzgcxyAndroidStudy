package cn.zeffect.apk.jump.myapplication2.unit39;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import cn.zeffect.apk.jump.myapplication2.R;

public class TwoButtonDialog extends DialogFragment implements View.OnClickListener {


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            DisplayMetrics dm = getResources().getDisplayMetrics();
//            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.8), (int)(dm.heightPixels*0.9));

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_two, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view.findViewById(R.id.sure_btn).setOnClickListener(this);
        view.findViewById(R.id.cancel_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sure_btn) {
            Toast.makeText(getContext(), "点击了确定按键", Toast.LENGTH_SHORT).show();
            this.dismiss();
        } else if (v.getId() == R.id.cancel_btn) {
            Toast.makeText(getActivity(), "点击了取消按钮", Toast.LENGTH_SHORT).show();
            this.dismiss();
        }
    }
}
