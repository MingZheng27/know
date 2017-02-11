package com.example.dell.zhihuknows2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dell on 2016/6/18.
 */
public class SearchViewAdapter extends BaseAdapter implements Filterable {
    private List<Users> list;
    private Context context;
    public SearchViewAdapter(List<Users> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Users getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.searchzilayout,null);   //convertView是判断是否为null后用来判断是否要重新引入子布局的！！！
        TextView textView = (TextView) view.findViewById(R.id.yonghu_name);
        ImageView imageView = (ImageView)view.findViewById(R.id.touxiang);
        imageView.setImageBitmap(list.get(position).getTouxiang());
        textView.setText(getItem(position).getUsersname());
        return view;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
