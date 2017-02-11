package com.example.dell.zhihuknows2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/6/16.
 */
public class AnswersActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private List<Paper> list = new ArrayList<>();
    private MyanswersAdapter myanswersAdapter;
    private String realdate;
    private String leix;
    private SQLiteDatabase sqLiteDatabase;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DBOpenHelper dbOpenHelper = new DBOpenHelper(AnswersActivity.this,"love",null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answerslayout);
        Intent intent = getIntent();
        realdate = intent.getStringExtra("date");
        leix = intent.getStringExtra("leixing");
        recyclerView = (RecyclerView)findViewById(R.id.answers_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        toolbar = (Toolbar)findViewById(R.id.answers_toolbar);
        toolbar.setTitle(R.string.content);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.answers_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        //toolbar.inflateMenu(R.menu.tool_bar_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //getSupportActionBar().setHomeButtonEnabled(true);
        myanswersAdapter = new MyanswersAdapter(list,dbOpenHelper.getWritableDatabase());
        MyAsyncTask1 myAsyncTask1 = new MyAsyncTask1();
        myAsyncTask1.execute("http://api.kanzhihu.com/getpostanswers/" + realdate + "/" + leix);
        recyclerView.setAdapter(myanswersAdapter);
        myanswersAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Paper p = list.get(position);
                Intent intent1 = new Intent(AnswersActivity.this,MyWebView.class);
                intent1.putExtra("questionId",p.getQuestionId());
                intent1.putExtra("answerId",p.getAnswerId());
                startActivity(intent1);
            }

            @Override
            public void onImageButtonClick(View view, int position) {
                if (list.get(position).isloved() == false){
                //DBOpenHelper dbOpenHelper = new DBOpenHelper(AnswersActivity.this,"love",null,1);//创建数据库的名字
                sqLiteDatabase = dbOpenHelper.getWritableDatabase();
                Cursor cursor = sqLiteDatabase.query("Loves",new String[]{"answerId"},"answerId = ?",new String[]{list.get(position).getAnswerId()},null,null,null);
                    if (cursor.getCount() != 0){//不能用!=null因为就算什么都没有也部位空,应该用getCount来判断是否有内容
                    Toast.makeText(AnswersActivity.this,"已存在收藏夹",Toast.LENGTH_SHORT).show();
                    return;
                        }
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("title",list.get(position).getMtitle());
                    contentValues.put("summary",list.get(position).getZhaiyao());
                    contentValues.put("questionId",list.get(position).getQuestionId());
                    contentValues.put("answerId",list.get(position).getAnswerId());
                    contentValues.put("touxurl",list.get(position).getTouxurl());
                    sqLiteDatabase.insert("Loves", null, contentValues);
                    Toast.makeText(AnswersActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                    list.get(position).setIsloved(true);
                    view.findViewById(R.id.love).setBackgroundResource(R.drawable.love);
                }
                else if (list.get(position).isloved() == true){
                    //删除收藏
                    sqLiteDatabase = dbOpenHelper.getWritableDatabase();
                    sqLiteDatabase.delete("Loves","answerId = ?",new String[]{list.get(position).getAnswerId()});
                    view.findViewById(R.id.love).setBackgroundResource(R.drawable.no_love);
                    Toast.makeText(AnswersActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                    list.get(position).setIsloved(false);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        myanswersAdapter = new MyanswersAdapter(list,dbOpenHelper.getWritableDatabase());
        MyAsyncTask1 myAsyncTask1 = new MyAsyncTask1();
        myAsyncTask1.execute("http://api.kanzhihu.com/getpostanswers/" + realdate + "/" + leix);
    }

    class MyAsyncTask1 extends AsyncTask<String,Integer,ArrayList<Paper>> {
        StringBuilder sb = new StringBuilder();
        @Override
        protected ArrayList<Paper> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(8000);
                conn.setReadTimeout(8000);
                conn.connect();
                BufferedReader bfr = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;
                while((line = bfr.readLine()) != null){
                    sb.append(line);
                }
                JSONObject jsonObject = new JSONObject(sb.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("answers");
                for (int i = 0 ;i < jsonArray.length() ; i++){
                    JSONObject jsonObject1 = (JSONObject)jsonArray.get(i);
                    Paper paper = new Paper();
                    paper.setMtitle(jsonObject1.getString("title"));
                    paper.setZhaiyao(jsonObject1.getString("summary"));
                    paper.setTouxurl(jsonObject1.getString("avatar"));
                    paper.setToux(BitmapFactory.decodeStream(new URL(jsonObject1.getString("avatar")).openStream()));
                    paper.setAnswerId(jsonObject1.getString("answerid"));
                    paper.setQuestionId(jsonObject1.getString("questionid"));
                    if (!list.contains(paper)){
                        list.add(paper);
                    }
                    //list.add(paper);
                }
            } catch (Exception e) {
                Toast.makeText(AnswersActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Paper> papers) {
            //listView.setAdapter(baseAdapter);
            super.onPostExecute(papers);
            myanswersAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }

    }
}
