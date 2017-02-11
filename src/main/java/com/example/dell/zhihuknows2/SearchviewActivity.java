package com.example.dell.zhihuknows2;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
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
 * Created by dell on 2016/6/18.
 */
public class SearchviewActivity extends AppCompatActivity {
    private SearchView searchView;
    private List<Users> list = new ArrayList<>();
    private ListView listView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchviewlayout);
        searchView = (SearchView)findViewById(R.id.search_view);
        listView = (ListView)findViewById(R.id.search_view_list);
        toolbar = (Toolbar)findViewById(R.id.search_view_bar);
        toolbar.setTitle(R.string.search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                AsyncTask02 asyncTask02 = new AsyncTask02();
                asyncTask02.execute("http://api.kanzhihu.com/searchuser/" + query );
                searchView.setIconified(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    class AsyncTask02 extends AsyncTask<String,Integer,List<Users>> {

        @Override
        protected void onPreExecute() {
            list.clear();
            super.onPreExecute();
        }

        @Override
        protected List<Users> doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(8000);
                conn.setReadTimeout(8000);
                conn.connect();
                BufferedReader bfr = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;
                while((line = bfr.readLine()) != null){
                    sb.append(line);
                    //Log.d("ccc",line);
                }
                JSONObject jsonObject = new JSONObject(sb.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("users");
                for (int i = 0 ;i < jsonArray.length() ; i++){
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    Users users = new Users();
                    users.setUsersname(jsonObject1.getString("name"));
                    users.setTouxiangurl(jsonObject1.getString("avatar"));
                    users.setUserhash(jsonObject1.getString("hash"));
                    URL url1 = new URL(users.getTouxiangurl());
                    HttpURLConnection connection = (HttpURLConnection)url1.openConnection();
                    users.setTouxiang(BitmapFactory.decodeStream(connection.getInputStream()));
                    list.add(users);
                }
            } catch (Exception e) {
                Toast.makeText(SearchviewActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Users> userses) {
            listView.setAdapter(baseAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //list.get(position).getUserhash();
                    Intent intent = new Intent(SearchviewActivity.this,Userdetail.class);
                    intent.putExtra("userhash",list.get(position).getUserhash());
                    startActivity(intent);
                }
            });
            super.onPostExecute(userses);
        }
    }
    private BaseAdapter baseAdapter = new BaseAdapter() {
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
            LayoutInflater layoutInflater = LayoutInflater.from(getBaseContext());
            View view = layoutInflater.inflate(R.layout.searchzilayout,null);   //convertView是判断是否为null后用来判断是否要重新引入子布局的！！！
            TextView textView = (TextView) view.findViewById(R.id.yonghu_name);
            ImageView imageView = (ImageView)view.findViewById(R.id.touxiang);
            imageView.setImageBitmap(list.get(position).getTouxiang());
            textView.setText(getItem(position).getUsersname());
            return view;
        }
    };
}
