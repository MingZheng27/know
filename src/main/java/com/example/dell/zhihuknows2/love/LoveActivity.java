package com.example.dell.zhihuknows2.love;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dell.zhihuknows2.DBOpenHelper;
import com.example.dell.zhihuknows2.MyWebView;
import com.example.dell.zhihuknows2.OnItemClickListener;
import com.example.dell.zhihuknows2.Paper;
import com.example.dell.zhihuknows2.R;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/6/18.
 */
public class LoveActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private Button okButton;
    private Button cancelButton;
    private Toolbar toolbar;
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private List<Paper> list = new ArrayList<>();
    private LoveAdapter loveAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lovelayout);
        toolbar = (Toolbar)findViewById(R.id.love_tool_bar);
        okButton = (Button)findViewById(R.id.love_delete);
        cancelButton = (Button)findViewById(R.id.love_cancel);
        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        recyclerView = (RecyclerView)findViewById(R.id.love_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        toolbar.setTitle(R.string.love);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dbOpenHelper = new DBOpenHelper(LoveActivity.this,"love",null,1);
        sqLiteDatabase = dbOpenHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("Loves",null,null,null,null,null,null);
        if (cursor != null && cursor.moveToFirst()){
            do {
                Paper paper = new Paper();
                paper.setTouxurl(cursor.getString(cursor.getColumnIndex("touxurl")));
                paper.setMtitle(cursor.getString(cursor.getColumnIndex("title")));
                paper.setZhaiyao(cursor.getString(cursor.getColumnIndex("summary")));
                paper.setQuestionId(cursor.getString(cursor.getColumnIndex("questionId")));
                paper.setAnswerId(cursor.getString(cursor.getColumnIndex("answerId")));
                list.add(paper);
            }while(cursor.moveToNext());//如果move成功了返回true,do while是条件为真循环啊!!!!
        }
        //start threads load toux
        for (int i = 0 ; i < list.size() ; i++){
            MAsyncTask mAsyncTask = new MAsyncTask(i);
            mAsyncTask.execute(list.get(i).getTouxurl());
        }
        loveAdapter = new LoveAdapter(list);
        loveAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(LoveActivity.this, MyWebView.class);
                intent.putExtra("questionId",list.get(position).getQuestionId());
                intent.putExtra("answerId",list.get(position).getAnswerId());
                startActivity(intent);
            }

            @Override
            public void onImageButtonClick(View view, int position) {

            }
        });
        recyclerView.setAdapter(loveAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.love_bar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_love:
                okButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
                //loveAdapter.setCheckBoxVisable();
                for (int i = 0;i <list.size() ; i++){
                    list.get(i).setCheckboxvisable(true);
                }
                loveAdapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.love_cancel:
                //loveAdapter.setCheckBoxGone();
                okButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
                for (int i = 0;i <list.size() ; i++){
                    list.get(i).setCheckboxvisable(false);
                }
                loveAdapter.notifyDataSetChanged();
                break;
            case R.id.love_delete:
                //loveAdapter.setCheckBoxGone();
                okButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
                for (int i = 0;i <list.size() ; i++){
                    list.get(i).setCheckboxvisable(false);
                }
                //loveAdapter.notifyDataSetChanged();
                for (int i = 0 ;i < list.size() ; i++){
                    if (list.get(i).isselected()){
                        sqLiteDatabase.delete("Loves","title = ?",new String[]{list.get(i).getMtitle()});//whereclause不需要where只需要表达式
                        list.remove(i);//假设2，3被选中2被remove后3就成为2若i++后则原来的3不会被remove所以要将i--;
                        i--;
                    }
                }
                loveAdapter.notifyDataSetChanged();
                break;
        }
    }

    class MAsyncTask extends AsyncTask<String,Integer,List<Paper>>{
        private int position;
        public MAsyncTask(int position) {
            this.position = position;
        }

        @Override
        protected List<Paper> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setReadTimeout(8000);
                httpURLConnection.setConnectTimeout(8000);
                list.get(position).setToux(BitmapFactory.decodeStream(httpURLConnection.getInputStream()));
            } catch (Exception e) {
                Toast.makeText(LoveActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    }

}
