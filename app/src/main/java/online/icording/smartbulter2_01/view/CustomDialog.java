package online.icording.smartbulter2_01.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import online.icording.smartbulter2_01.R;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.view
 * 创建时间   2018/4/17 0017 13:20
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class CustomDialog extends Dialog {
    //定义模板
    public CustomDialog(Context context,int layout,int style){
        this(context, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,layout,style, Gravity.CENTER);
    }
    //定义属性
    public CustomDialog(Context context,int width,int height,int layout,int style,int gravity,int anim){
        super(context, style);
        //
        setContentView(layout);
        Window window=getWindow();

        WindowManager.LayoutParams layoutParams=window.getAttributes();
        layoutParams.height=height;
        layoutParams.width=width;
        layoutParams.gravity=gravity;

        window.setAttributes(layoutParams);
        window.setWindowAnimations(anim);
    }
    //实例
    public CustomDialog(Context context,int width,int height,int layout,int style,int gravity){
        this(context, width, height, layout, style, gravity,R.style.Pop_anim_style);
    }
}

