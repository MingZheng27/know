package com.example.dell.zhihuknows2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dell on 2016/6/16.
 */
public class AnsersViewHolder extends RecyclerView.ViewHolder{
    public TextView textView00;
    public CircleImageView circleImageView00;
    public TextView textView01;
    public AnsersViewHolder(View itemView) {
        super(itemView);
        textView00 = (TextView)itemView.findViewById(R.id.answers_neir);
        circleImageView00 = (CircleImageView)itemView.findViewById(R.id.answers_toux);
        textView01 = (TextView)itemView.findViewById(R.id.m_title);
    }
}
