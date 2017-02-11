package com.example.dell.zhihuknows2;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dell on 2016/5/6.
 */
public class MyAdapter extends RecyclerView.Adapter{
    private List<FrameWork> list;
    public CardView cardView;
    private OnItemClickListener mOnItemClickListener;
    public MyAdapter(List<FrameWork> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//这个只是引入布局,在ViewHolder中findViewById
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycleviewlayout,parent,false);
        cardView = (CardView) view.findViewById(R.id.my_cardview);
        //cardView.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {//在这里对控件渲染内容进行设置
        TextView textView = ((ViewHolder)holder).textView1;
        textView.setText(list.get(position).getNeirong());
        ImageView imageView = ((ViewHolder)holder).myImageView;
        imageView.setImageBitmap(list.get(position).getTouxiangheji());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }//通过getItemCount就可知道要实例化多少个RecyclerView的子布局，即多少个CardView

    public void setOnItemClickLitener(OnItemClickListener mOnItemClickListerner) {
        this.mOnItemClickListener = mOnItemClickListerner;
    }
}
