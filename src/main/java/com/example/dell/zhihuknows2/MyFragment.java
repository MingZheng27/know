package com.example.dell.zhihuknows2;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by dell on 2016/6/12.
 */
public class MyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView myrecyclerView;
    private List<FrameWork> list = new ArrayList<FrameWork>();
    public MyAdapter myAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static final int RECENT = 0;
    public static final int YESTERDAY = 1;
    public static final int ARCHIVE = 2;
    private int zhonglei;

    public MyFragment(int zhonglei) {
        this.zhonglei = zhonglei;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentlayout,null);
        myrecyclerView = (RecyclerView)view.findViewById(R.id.my_recyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.my_swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        myAdapter = new MyAdapter(list);
        myrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myrecyclerView.setAdapter(myAdapter);
        MyAsyncTask myAsyncTask = new MyAsyncTask("http://api.kanzhihu.com/getposts");
        myAsyncTask.execute();
        myAdapter.setOnItemClickLitener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                FrameWork frame = list.get(position);
                Intent intent = new Intent(getActivity(),AnswersActivity.class);//必须用接口将方法回调才能startAcitivity
                intent.putExtra("date",frame.getDate());
                intent.putExtra("leixing",frame.getName());
                startActivity(intent);
            }

            @Override
            public void onImageButtonClick(View view, int position) {

            }
        });
        return view;
    }

    @Override
    public void onRefresh() {
        //list.clear();
        MyAsyncTask myAsyncTask = new MyAsyncTask("http://api.kanzhihu.com/getposts");
        myAsyncTask.execute();//test后面要改AsyncTask里面的内容改成没有变的就不动，变得就加
        //myAdapter.notifyDataSetChanged();execute里面notify了
    }

    class MyAsyncTask extends AsyncTask<String,Integer,List<Fragment>> {
        private StringBuilder sb = new StringBuilder();
        private URL url;
        public MyAsyncTask(String url) {
            try {
                this.url = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected List<Fragment> doInBackground(String... params) {
            try {
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
                //Log.d("eee","eee");
            } catch (Exception e) {
                //Log.d("aaa","访问出问题了");
                e.printStackTrace();
            }
            try {
                JSONObject jsonObject = new JSONObject(sb.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("posts");
                //Log.d("f","fff");
                if (zhonglei == RECENT){
                    for (int i = 0 ;i < 10 ; i = i + 3){
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);//这里拿到JSON数组以后直接用get(i)然后强转，用getJSONObject后面参数必须要是字符
                        //而且如果用0啊什么的是不行的因为那个不是字符串啊，不是绿字，是黑字绿的才是字符串
                        FrameWork frameWork = new FrameWork();
                        frameWork.setNeirong(jsonObject1.getString("excerpt"));
                        frameWork.setTouxiangheji(BitmapFactory.decodeStream(new URL(jsonObject1.getString("pic")).openStream()));
                        String fataldate = jsonObject1.getString("date");
                        String[] strings = fataldate.split("-");
                        String realdate = strings[0] + strings[1] + strings[2];
                        frameWork.setDate(realdate);
                        frameWork.setName(jsonObject1.getString("name"));
                        if (!list.contains(frameWork)){
                            list.add(frameWork);
                        }
                        //Log.d("a","aaa");
                    }}
                if (zhonglei == YESTERDAY){
                    for (int i = 1 ;i < 10 ; i = i + 3){
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        FrameWork frameWork = new FrameWork();
                        frameWork.setNeirong(jsonObject1.getString("excerpt"));
                        String fataldate = jsonObject1.getString("date");
                        String[] strings = fataldate.split("-");
                        String realdate = strings[0] + strings[1] + strings[2];
                        frameWork.setDate(realdate);
                        frameWork.setTouxiangheji(BitmapFactory.decodeStream(new URL(jsonObject1.getString("pic")).openStream()));
                        frameWork.setName(jsonObject1.getString("name"));
                        if (!list.contains(frameWork)){
                            list.add(frameWork);
                        }
                    }}
                if (zhonglei == ARCHIVE){
                    for (int i = 2 ;i < 10 ; i = i + 3){
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        FrameWork frameWork = new FrameWork();
                        frameWork.setNeirong(jsonObject1.getString("excerpt"));
                        String fataldate = jsonObject1.getString("date");
                        String[] strings = fataldate.split("-");
                        String realdate = strings[0] + strings[1] + strings[2];
                        frameWork.setDate(realdate);
                        frameWork.setTouxiangheji(BitmapFactory.decodeStream(new URL(jsonObject1.getString("pic")).openStream()));
                        frameWork.setName(jsonObject1.getString("name"));
                        if (!list.contains(frameWork)){
                            list.add(frameWork);
                        }
                    }}
            } catch (Exception e) {
               // Toast.makeText(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Fragment> fragments) {
            //Fragment01 fragment = (Fragment01) MainActivity.list.get(d);//已经实例化出来了，可以直接访问，不需要用static
            //textView.setText(fragment.content);
            //imageView.setImageBitmap(fragment.bitmap);

            //Fragment02.fragmentPagerAdapter.notifyDataSetChanged();
            myAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
            MainActivity.getFragmentPagerAdapter().notifyDataSetChanged();
            //Log.d("b","bbb");
            super.onPostExecute(fragments);
        }
    }
}
