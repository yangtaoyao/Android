package online.icording.smartbulter2_01.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.adapter.ZhiHuAdapter;
import online.icording.smartbulter2_01.entity.ZhiHuData;
import online.icording.smartbulter2_01.utils.L;
import online.icording.smartbulter2_01.view.TitleBuilder;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.ui
 * 创建时间   2018/4/22 0022 22:48
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class WebViewZhiHuActivity extends BaseActivity {

    private WebView mWebView;
    private ProgressBar mProgressBar;
    private String share_url;//webView打开的URL
//    final List<String> body= new ArrayList<>();
    String title;
    String html = "<html>";
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        //默认打开浏览器
//        Uri uri=Uri.parse("http://www.baidu.com");
//        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
//        startActivity(intent);

        new TitleBuilder(WebViewZhiHuActivity.this).setTitleText(title)
                .setLeftImage(R.drawable.backicon)
                .setLightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
        ;

        initView();
    }

    private void initView() {
        mProgressBar=(ProgressBar) findViewById(R.id.mProgressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        mWebView=(WebView) findViewById(R.id.mWebView);

        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        final String url=intent.getStringExtra("url");

        class Httpcallback extends HttpCallback{

            public String getBody() {
                return body;
            }

            private String body;
            @Override
            public void onSuccess(String t) {
                //L.i(t);
                try {
                    JSONObject jsonObject=new JSONObject(t);
                    body="<body>"+jsonObject.getString("body")+"</body>";

                    //
                    // Toast.makeText(WebViewZhiHuActivity.this, body,Toast.LENGTH_SHORT).show();
                    html="<html>"+ body +"</html>";
//        L.i(body);
                    //Toast.makeText(WebViewZhiHuActivity.this,html,Toast.LENGTH_SHORT).show();
                    //设置标题
                    //getSupportActionBar().setTitle(title);

                    //加载网页

                    //支持js
                    mWebView.getSettings().setJavaScriptEnabled(true);
                    //支持缩放
                    mWebView.getSettings().setSupportZoom(true);
                    mWebView.getSettings().setBuiltInZoomControls(true);
                    //接口回调
                    //mWebView.setWebChromeClient(new webViewClient());
                    //加载网页
//        mWebView.loadUrl(url);
//
//        //本地显示
//        mWebView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
//               view.loadUrl(url);
//                return true;
//            }
//        });


                    mWebView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
                    mWebView.setWebChromeClient(new WebChromeClient());

                    //优先加载缓冲
                    mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        Httpcallback httpcallback=new Httpcallback();
        RxVolley.get(url,httpcallback);

//        RxVolley.get(url, new HttpCallback() {
//            @Override
//            public void onSuccess(String t) {
//                L.i(t);
//                try {
//                    JSONObject jsonObject=new JSONObject(t);
//                     String s="<body>"+jsonObject.getString("body")+"</body>";
//                    body.add(s);
//                    Toast.makeText(WebViewZhiHuActivity.this, body.get(0),Toast.LENGTH_SHORT).show();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });

    }

//    private void parsingJson(String t) {
//        try {
//            JSONObject jsonObject=new JSONObject(t);
//            share_url=jsonObject.getString("share_url");
//            image=jsonObject.getString("image");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

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
