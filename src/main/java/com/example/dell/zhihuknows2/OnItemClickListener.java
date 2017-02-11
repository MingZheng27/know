package com.example.dell.zhihuknows2;

import android.view.View;

/**
 * Created by dell on 2016/6/16.
 */
public interface OnItemClickListener {

    void onItemClick(View view,int position);

    void onImageButtonClick(View view,int position);//这里一定要有view的参数,因为这样才能拿到单个的CardView
}

