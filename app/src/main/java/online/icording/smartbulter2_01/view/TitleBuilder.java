package online.icording.smartbulter2_01.view;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import online.icording.smartbulter2_01.R;

/**
 * 项目名     SmartBulter2.01
 * 包名      online.icording.smartbulter2_01.view
 * 创建时间   2018/4/28 0028 17:54
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class TitleBuilder {

    private View viewTitle;
    private ImageView ivLeft;
    private TextView tvLeft;
    private TextView tvTitle;
    private ImageView ivRight;
    private ImageView ivRight02;
    private TextView tvRight;

    //activity 中使用
    public TitleBuilder(Activity context){
        viewTitle=context.findViewById(R.id.rl_titlebar);
        tvTitle=viewTitle.findViewById(R.id.titlebar_tv);
        tvLeft=viewTitle.findViewById(R.id.titlebar_tv_left);
        ivLeft=viewTitle.findViewById(R.id.titlebar_iv_left);
        tvRight=viewTitle.findViewById(R.id.titlebar_tv_right);
        ivRight=viewTitle.findViewById(R.id.titlebar_iv_right);
        ivRight02=viewTitle.findViewById(R.id.titlebar_iv_right02);
    }
    //fragment 中使用
    public TitleBuilder(View context){
        viewTitle=context.findViewById(R.id.rl_titlebar);
        tvTitle=viewTitle.findViewById(R.id.titlebar_tv);
        tvLeft=viewTitle.findViewById(R.id.titlebar_tv_left);
        ivLeft=viewTitle.findViewById(R.id.titlebar_iv_left);
        tvRight=viewTitle.findViewById(R.id.titlebar_tv_right);
        ivRight=viewTitle.findViewById(R.id.titlebar_iv_right);
        ivRight02=viewTitle.findViewById(R.id.titlebar_iv_right02);
    }

    //
    public TitleBuilder setBgRes(int resid){
        viewTitle.setBackgroundResource(resid);
        return this;
    }
    //title
    public TitleBuilder setTitleBgRes(int resid){
        viewTitle.setBackgroundResource(resid);
        return this;
    }

    public TitleBuilder setTitleText(String text){
        tvTitle.setVisibility(TextUtils.isEmpty(text)?View.GONE:View.VISIBLE);
        tvTitle.setText(text);
        return this;
    }

    //left
    public TitleBuilder setLeftImage(int resid){
        ivLeft.setVisibility(resid>0?View.VISIBLE:View.GONE);
        ivLeft.setImageResource(resid);
        return this;
    }
    public TitleBuilder setLeftText(String text){
        tvLeft.setVisibility(TextUtils.isEmpty(text)?View.GONE:View.VISIBLE);
        tvLeft.setText(text);
        return this;
    }
    public TitleBuilder setLightOnClickListener(View.OnClickListener listener){
        if(ivLeft.getVisibility()==View.VISIBLE){
            ivLeft.setOnClickListener(listener);
        }else if(tvLeft.getVisibility()==View.VISIBLE){
            tvLeft.setOnClickListener(listener);
        }
        return this;
    }


    //Right
    public TitleBuilder setRightImage(int resid){
        ivRight.setVisibility(resid>0?View.VISIBLE:View.GONE);
        ivRight.setImageResource(resid);
        return this;
    }
    public TitleBuilder setRightImage02(int resid){
        ivRight02.setVisibility(resid>0?View.VISIBLE:View.GONE);
        ivRight02.setImageResource(resid);
        return this;
    }
    public TitleBuilder setRightText(String text){
        tvRight.setVisibility(TextUtils.isEmpty(text)?View.GONE:View.VISIBLE);
        tvRight.setText(text);
        return this;
    }
    public TitleBuilder setRightOnClickListener(View.OnClickListener listener){
        if(ivRight.getVisibility()==View.VISIBLE){
            ivRight.setOnClickListener(listener);
        }else if(tvRight.getVisibility()==View.VISIBLE){
            tvRight.setOnClickListener(listener);
        }
        return this;
    }
    public TitleBuilder setRight02OnClickListener(View.OnClickListener listener){
        if(ivRight02.getVisibility()==View.VISIBLE) {
            ivRight02.setOnClickListener(listener);
        }
        return this;
    }
    public View build(){
        return viewTitle;
    }


}
