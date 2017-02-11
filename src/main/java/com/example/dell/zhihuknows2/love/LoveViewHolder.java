package com.example.dell.zhihuknows2.love;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.dell.zhihuknows2.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dell on 2016/6/18.
 */
public class LoveViewHolder extends RecyclerView.ViewHolder {
    public CircleImageView lovecirimageview;
    public TextView lovetitle;
    public TextView loveneir;
    public CheckBox checkBox;
    public LoveViewHolder(View itemView) {
        super(itemView);
        lovecirimageview = (CircleImageView)itemView.findViewById(R.id.love_toux);
        lovetitle = (TextView)itemView.findViewById(R.id.love_title);
        loveneir = (TextView)itemView.findViewById(R.id.love_neir);
        checkBox = (CheckBox)itemView.findViewById(R.id.love_checkbox);
    }
}
