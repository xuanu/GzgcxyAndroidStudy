package cn.zeffect.apk.jump.myapplication2.unit25;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.zeffect.apk.jump.myapplication2.R;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.MyViewHolder> {

    private List<GameInfo> gameInfos;

    public GameAdapter(List<GameInfo> gameInfos) {
        this.gameInfos = gameInfos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //这个就是我们写的子布局
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //用于修改值。数据从哪里来？
        GameInfo tmpInfo = gameInfos.get(position);
        holder.nameTv.setText(tmpInfo.getName());
        //所有对界面上的修改，在这里来做。
    }

    @Override
    public int getItemCount() {
        return gameInfos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTv;
        private ImageView appImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.item_app_name_tv);
            appImg = itemView.findViewById(R.id.item_app_img);
        }
    }
}
