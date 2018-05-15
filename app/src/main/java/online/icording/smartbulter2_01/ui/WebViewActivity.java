package online.icording.smartbulter2_01.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.view.CustomPopWindow;
import online.icording.smartbulter2_01.view.TitleBuilder;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.ui
 * 创建时间   2018/4/22 0022 22:48
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class WebViewActivity extends BaseActivity {

    String url;
    String title;
    private WebView mWebView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        //默认打开浏览器
//        Uri uri=Uri.parse("http://www.baidu.com");
//        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
//        startActivity(intent);

        initView();
        new TitleBuilder(this).setTitleText(title)
                .setLeftImage(R.drawable.backicon)
                .setRightImage(R.drawable.share)
                .setLightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(),"分享",Toast.LENGTH_SHORT).show();
                    }
        })
        ;
    }

    private void initView() {
        mProgressBar=(ProgressBar) findViewById(R.id.mProgressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        mWebView=(WebView) findViewById(R.id.mWebView);

        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        url=intent.getStringExtra("url");

        //设置标题
        //getSupportActionBar().setTitle(title);

        //mWebView.loadDataWithBaseURL(null,data+"","html/text","utf-8",null);
        //加载网页
        //支持js
        mWebView.getSettings().setJavaScriptEnabled(true);
        //支持缩放
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        //接口回调
        mWebView.setWebChromeClient(new webViewClient());
        //加载网页
        mWebView.loadUrl(url);

        //本地显示
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                view.loadUrl(url);
                return true;
            }
        });

        //优先加载缓冲
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }
    //加载进度监听
    public class webViewClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress==100){
                mProgressBar.setVisibility(View.GONE);
            }else {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            super.onProgressChanged(view, newProgress);
        }
    }

    //改写返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(mWebView.canGoBack()){
                mWebView.goBack();//返回
                return true;
            }else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
