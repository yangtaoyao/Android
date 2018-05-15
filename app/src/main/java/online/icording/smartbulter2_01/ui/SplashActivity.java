package online.icording.smartbulter2_01.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import online.icording.smartbulter2_01.MainActivity;
import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.utils.ShareUtils;
import online.icording.smartbulter2_01.utils.StaticClass;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.ui
 * 创建时间   2018/4/15 0015 1:16
 * 创建者    yangtaoyao
 * 描述      闪屏页
 **/
public class SplashActivity extends AppCompatActivity {

    private TextView tv_splash;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是否第一次运行
                    if(isFirst()){
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                        finish();
                    }else {
                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
                        finish();
                    }
                    break;
            }
        }
    };

    //判断是否首次运行
    private boolean isFirst() {
        boolean isFirst= ShareUtils.getBoolean(this,StaticClass.SHARE_IS_FIRST,true);
        if(isFirst){
            ShareUtils.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();

    }

    //初始化View
    private void initView() {
        //延时2000ms
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);

        tv_splash=findViewById(R.id.tv_splash);

    }

    //禁止返回键
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
