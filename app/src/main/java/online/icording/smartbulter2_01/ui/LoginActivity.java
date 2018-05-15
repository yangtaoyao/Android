package online.icording.smartbulter2_01.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import online.icording.smartbulter2_01.MainActivity;
import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.entity.Myuser;
import online.icording.smartbulter2_01.utils.ShareUtils;
import online.icording.smartbulter2_01.utils.StaticClass;
import online.icording.smartbulter2_01.view.CustomDialog;
import online.icording.smartbulter2_01.view.CustomPopWindow;
import online.icording.smartbulter2_01.view.TitleBuilder;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.ui
 * 创建时间   2018/4/16 0016 9:06
 * 创建者    yangtaoyao
 * 描述     登录
 **/
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_registered;
    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private TextView forget_password;
    private CustomDialog mDialog;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {

        //Dialog
        mDialog=new CustomDialog(this,300,300,R.layout.dialog_loading,R.style.Theme_dialog, Gravity.CENTER,R.style.Pop_anim_style);
        //屏幕外点击无效
        mDialog.setCancelable(false);
        //设置标题栏
        new TitleBuilder(this).setBgRes(R.color.colorTransparent)
                .setRightImage(R.drawable.quxiao)
                .setRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);
        btn_login=findViewById(R.id.btn_login);
        tv_registered= findViewById(R.id.tv_registered);
        forget_password=findViewById(R.id.forgetpassword);

        tv_registered.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        forget_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_registered:
                startActivity(new Intent(this,RegisteredActivty.class));
                break;
            case R.id.btn_login:

                //获取输入框的值
                String name=et_username.getText().toString().trim();
                String password=et_password.getText().toString().trim();
                //判断是否为空
                if(!TextUtils.isEmpty(name)&!TextUtils.isEmpty(password)){

                    mDialog.show();

                    //登录
                    final Myuser user=new Myuser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<Myuser>() {
                        @Override
                        public void done(Myuser myuser, BmobException e) {

                            mDialog.dismiss();

                            if(e==null){
                                //判断邮箱是否验证
                                if(user.getEmailVerified()){
                                    //设置登录状态
                                    ShareUtils.putBoolean(LoginActivity.this, StaticClass.ALREADY_LOGIN,true);
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(LoginActivity.this,"请前往邮箱验证！",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(LoginActivity.this,"登录失败！"+e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(LoginActivity.this,"输入框不能为空！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.forgetpassword:
                startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
                break;
        }
    }
}
