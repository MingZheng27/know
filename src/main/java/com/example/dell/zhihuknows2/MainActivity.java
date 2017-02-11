package com.example.dell.zhihuknows2;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import com.example.dell.zhihuknows2.love.LoveActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<MyFragment> list= new ArrayList<>();
    private SlidingTabLayout slidingTabLayout;
    private Toolbar toolbar;
    private String[] titles;
    //private CardView cardView;
    private static FragmentPagerAdapter fragmentPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager)findViewById(R.id.m_viewpager);
        toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        //toolbar.inflateMenu(R.menu.tool_bar_menu);用了后面的setSupportActionBar就不能用inflateMenu了,因为toolbar已经当作ActionBar来用了,只有单独做一个
        //控件的时候才能用inflateMenu
        slidingTabLayout = (SlidingTabLayout)findViewById(R.id.m_tab);
        //cardView = (CardView)findViewById(R.id.my_cardview);
        setSupportActionBar(toolbar);
        titles = new String[]{"昨日最新","近日热门","历史精华"};
        //getSupportActionBar().setHomeButtonEnabled(true);首页不需要
        /*ActionBar actionbar = getActionBar();
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionbar.addTab(actionbar.newTab().setText("昨日最新").setTabListener(this));
        actionbar.addTab(actionbar.newTab().setText("近日热门").setTabListener(this));
        actionbar.addTab(actionbar.newTab().setText("历史精华").setTabListener(this));*/
        for (int i = 0 ;i < 3 ; i++){
            MyFragment myFragment = new MyFragment(i);
            list.add(myFragment);
        }
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }//匿名内部类方式也可以覆写其实其他类也可以单独覆写某个方法
        };
        mViewPager.setAdapter(fragmentPagerAdapter);
        slidingTabLayout.setViewPager(mViewPager);//这句应该放在设定完Adapter后，否则ViewPager为空
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tool_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search_user:
                Intent intent = new Intent(MainActivity.this,SearchviewActivity.class);
                startActivity(intent);
                break;
            case R.id.shou_cang:
                Intent intent1 = new Intent(MainActivity.this, LoveActivity.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static FragmentPagerAdapter getFragmentPagerAdapter() {
        return fragmentPagerAdapter;
    }
}
