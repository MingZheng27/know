package com.example.dell.zhihuknows2;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
 * Created by dell on 2016/6/17.
 */
public class SearchActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button;
    private EditText editText;
    private ListView listView;
    private List<Users> list = new ArrayList<>();
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlayout);
        button = (Button) findViewById(R.id.searchs);
        editText = (EditText)findViewById(R.id.search_text);
        listView = (ListView)findViewById(R.id.search_list);
        toolbar = (Toolbar)findViewById(R.id.search_toolbar);
        toolbar.setTitle(R.string.search);
        setSupportActionBar(toolbar);//因为设置返回键是作为Actionbar的属性了,所以要用getSupportActionBar,如果是单纯做toolbar那就不用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button.setOnClickListener(this);
        //layoutInflater = inflater;//Fragment里的inflater不仅能用于引入Fragment布局也可以引入Adapter也就是LsitView的子布局的
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tool_bar_menu,menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.searchs:
                String a = editText.getText().toString();
                AsyncTask02 asyncTask02 = new AsyncTask02();
                asyncTask02.execute("http://api.kanzhihu.com/searchuser/" + a );
                break;
            default:
                break;
        }
    }

    class AsyncTask02 extends AsyncTask<String,Integer,List<Users>> {

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
                Toast.makeText(SearchActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
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
                    Intent intent = new Intent(SearchActivity.this,Userdetail.class);
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
