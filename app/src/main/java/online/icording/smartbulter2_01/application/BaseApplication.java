package online.icording.smartbulter2_01.application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

import cn.bmob.v3.Bmob;
import online.icording.smartbulter2_01.utils.StaticClass;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.application
 * 创建时间   2018/4/14 0014 11:50
 * 创建者    yangtaoyao
 * 描述
 **/
public class BaseApplication extends Application{


    @Override
    public void onCreate() {
        super.onCreate();

        //默认初始化BMOB
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        //SDKInitializer.initialize(this);
    }
}
