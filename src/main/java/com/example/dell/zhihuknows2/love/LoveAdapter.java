package com.example.dell.zhihuknows2.love;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.dell.zhihuknows2.OnItemClickListener;
import com.example.dell.zhihuknows2.Paper;
import com.example.dell.zhihuknows2.R;

import java.util.List;

/**
 * Created by dell on 2016/6/18.
 */
public class LoveAdapter extends RecyclerView.Adapter {
    private List<Paper> list;
    public LoveAdapter(List<Paper> list) {
        this.list = list;
    }
    private OnItemClickListener onItemClickListener;
    private CardView cardView;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.lovezilayout, parent,false);//这个false不能漏掉!!!!
        cardView = (CardView)view.findViewById(R.id.love_card_view);
        LoveViewHolder loveViewHolder = new LoveViewHolder(view);
        return loveViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((LoveViewHolder)holder).lovetitle.setText(list.get(position).getMtitle());
        ((LoveViewHolder)holder).loveneir.setText(list.get(position).getZhaiyao());
        ((LoveViewHolder)holder).lovecirimageview.setImageBitmap(list.get(position).getToux());
        ((LoveViewHolder)holder).checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    list.get(position).setIsselected(true);
                }else {
                    list.get(position).setIsselected(false);
                }
            }
        });
        if (list.get(position).isCheckboxvisable()){
            ((LoveViewHolder)holder).checkBox.setVisibility(View.VISIBLE);
        }else if (!list.get(position).isCheckboxvisable()){
            ((LoveViewHolder)holder).checkBox.setVisibility(View.GONE);
        }//渲染是否可见肯定只能在这里渲染啊...所以要在实体类里面加个visable的变量,
        if (list.get(position).isselected()){
            ((LoveViewHolder)holder).checkBox.setChecked(true);
        }else if (!list.get(position).isselected()){
            ((LoveViewHolder)holder).checkBox.setChecked(false);
        }
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;//写方法没有调用不需要实例化也就不需要实现方法
    }
}
