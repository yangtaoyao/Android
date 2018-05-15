package online.icording.smartbulter2_01.utils;

import android.util.Log;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.untils
 * 创建时间   2018/4/15 0015 0:27
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class L {
    //开关
    public static final boolean DEBUG=true;
    //TAG
    public static final String TAG="SmartBulter";

    //五个等级
    public static void d(String text){
        if(DEBUG==true){
            Log.d(TAG,text);
        }
    }

    public static void i(String text){
        if(DEBUG==true){
            Log.i(TAG,text);
        }
    }

    public static void e(String text){
        if(DEBUG==true){
            Log.e(TAG,text);
        }
    }

    public static void w(String text){
        if(DEBUG==true){
            Log.w(TAG,text);
        }
    }
}
