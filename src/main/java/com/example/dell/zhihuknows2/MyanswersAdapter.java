package com.example.dell.zhihuknows2;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dell on 2016/6/16.
 */
public class MyanswersAdapter extends RecyclerView.Adapter {
    private List<Paper> list;
    private OnItemClickListener onItemClickListener;
    private CardView cardView;
    private ImageButton imageButton;
    private SQLiteDatabase sqLiteDatabase;
    public MyanswersAdapter(List<Paper> list,SQLiteDatabase sqLiteDatabase) {
        this.list = list;
        this.sqLiteDatabase = sqLiteDatabase;
    }
    /*
    放这里写的不规范，应该是只在这里引入布局，在viewholder中findViewById
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.answerszilayout,parent,false);
        cardView = (CardView)view.findViewById(R.id.answers_card);
        imageButton = (ImageButton)view.findViewById(R.id.love);
        return new AnsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TextView tv = ((AnsersViewHolder)holder).textView00;
        CircleImageView civ = ((AnsersViewHolder)holder).circleImageView00;
        TextView tv1 = ((AnsersViewHolder)holder).textView01;
        //if (list.get(position).isloved()){
        //  imageButton.setBackgroundResource(R.drawable.love);返回了以后Acitivity被finish掉了，所以list的内存被GC释放掉了必须要从数据库中查才可以
        //}
        String answerid = list.get(position).getAnswerId();
        Cursor cursor = sqLiteDatabase.query("Loves", new String[]{"answerId"}, "answerId = ?", new String[]{answerid}, null, null, null);
        if (cursor.getCount() != 0){
            imageButton.setBackgroundResource(R.drawable.love);
            list.get(position).setIsloved(true);
        }
        tv1.setText(list.get(position).getMtitle());
        tv.setText(list.get(position).getZhaiyao());
        civ.setImageBitmap(list.get(position).getToux());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, position);
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onImageButtonClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
