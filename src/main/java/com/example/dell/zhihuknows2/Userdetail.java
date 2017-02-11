package com.example.dell.zhihuknows2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dell on 2016/5/17.
 */
public class Userdetail extends AppCompatActivity {
    private String userhash;
    private CircleImageView circleImageView;
    private TextView name;
    private TextView signnature;
    private TextView zannumber;
    private TextView answernumber;
    private TextView questionnumber;
    private TextView zhuanlannumber;
    private TextView favnumber;
    private TextView thxnumber;
    private TextView follower;
    private TextView followee;
    private Users user;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdetaillayout);
        toolbar = (Toolbar)findViewById(R.id.detail_toolbar);
        toolbar.setTitle(R.string.users_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        userhash = intent.getStringExtra("userhash");
        init();
        String aaa = "http://api.kanzhihu.com/userdetail2/" + userhash;
        MyAsyncTask04 myAsyncTask = new MyAsyncTask04();
        myAsyncTask.execute(aaa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tool_bar_menu,menu);//将布局引入到menu里
        return true;
    }

    private void init() {
        circleImageView = (CircleImageView)findViewById(R.id.profile_image);
        name = (TextView)findViewById(R.id.name_detail);
        signnature = (TextView)findViewById(R.id.sign_nature_detail);
        zannumber = (TextView)findViewById(R.id.zan_number_detail);
        answernumber = (TextView)findViewById(R.id.answer_number_detail);
        questionnumber = (TextView)findViewById(R.id.question_number_detail);
        zhuanlannumber = (TextView)findViewById(R.id.zhuanlan_number_detail);
        favnumber = (TextView)findViewById(R.id.fav_number_detail);
        thxnumber = (TextView)findViewById(R.id.thx_number_detail);
        followee = (TextView)findViewById(R.id.followee_number_detail);
        follower = (TextView)findViewById(R.id.follower_number_detail);
        user = new Users();
    }

    class MyAsyncTask04 extends AsyncTask<String,Integer,Object>{

        @Override
        protected Object doInBackground(String... params) {
            try {
                StringBuilder sb = new StringBuilder();
                String line = null;
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = bf.readLine()) != null){
                    sb.append(line);
                }
                JSONObject jsonObject = new JSONObject(sb.toString());
                JSONObject jsonObjectd = jsonObject.getJSONObject("detail");
                user.setUsersname(jsonObject.getString("name"));
                //user.setTouxiangurl(jsonObject.getString("avatar"));
                URL url1 = new URL(jsonObject.getString("avatar"));
                HttpURLConnection connection = (HttpURLConnection)url1.openConnection();
                user.setTouxiang(BitmapFactory.decodeStream(connection.getInputStream()));
                user.setSignnature(jsonObject.getString("signature"));
                user.setFollower(jsonObjectd.getInt("follower"));
                user.setFollowee(jsonObjectd.getInt("followee"));
                user.setAnswer(jsonObjectd.getInt("answer"));
                user.setQuestion(jsonObjectd.getInt("ask"));
                user.setPost(jsonObjectd.getInt("post"));
                user.setAgree(jsonObjectd.getInt("agree"));
                user.setFav(jsonObjectd.getInt("fav"));
                user.setThx(jsonObjectd.getInt("thanks"));
            } catch (Exception e) {
                Toast.makeText(Userdetail.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            circleImageView.setImageBitmap(user.getTouxiang());
            signnature.setText("个性签名:" + user.getSignnature());
            name.setText(user.getUsersname());
            follower.setText("被关注数:" + user.getFollower().toString());
            followee.setText("关注数:" + user.getFollowee().toString());
            answernumber.setText("回答数:" + user.getAnswer().toString());
            questionnumber.setText("提问数:" + user.getQuestion().toString());
            zhuanlannumber.setText("专栏数:" + user.getPost().toString());
            zannumber.setText("赞同数:" + user.getAgree().toString());
            favnumber.setText("喜欢数:" + user.getFav().toString());
            thxnumber.setText("被感谢数:" + user.getThx().toString());
            super.onPostExecute(o);
        }
    };
}
