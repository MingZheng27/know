package com.example.dell.zhihuknows2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URL;

/**
 * Created by dell on 2016/6/16.
 */
public class MyWebView extends AppCompatActivity {
    private Toolbar toolbar;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myweblayout);
        Intent intent = getIntent();
        String questionId = intent.getStringExtra("questionId");
        String answerId = intent.getStringExtra("answerId");
        webView = (WebView)findViewById(R.id.my_webview);
        toolbar = (Toolbar)findViewById(R.id.webview_toolbar);
        toolbar.setTitle(R.string.detail);
        //toolbar.inflateMenu(R.menu.tool_bar_menu);
        setSupportActionBar(toolbar);
        webView.loadUrl("https://www.zhihu.com/question/" + questionId + "/answer/" + answerId);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//要先设定上去才能改这个
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;//点击的时候是否跳转到外部浏览器
            }
        });
    }
}
