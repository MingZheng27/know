package com.example.dell.zhihuknows2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dell on 2016/5/6.
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView textView1;
    public ImageView myImageView;
    public ViewHolder(View itemView) {
        super(itemView);
        //textView1 = (TextView)itemView.findViewById(R.id.neirong);
        //textView1.setOnClickListener(this);
        textView1 = (TextView)itemView.findViewById(R.id.dagai);
        myImageView = (ImageView)itemView.findViewById(R.id.touxiang_heji);
    }
}
