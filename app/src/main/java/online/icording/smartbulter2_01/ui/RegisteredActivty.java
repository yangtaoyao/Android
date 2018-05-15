package online.icording.smartbulter2_01.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.entity.Myuser;
import online.icording.smartbulter2_01.view.TitleBuilder;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.ui
 * 创建时间   2018/4/16 0016 9:12
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class RegisteredActivty extends BaseActivity implements View.OnClickListener {

    private EditText et_username;
    private EditText et_age;
    private EditText et_desc;
    private RadioGroup mRadioGroup;
    private EditText et_passs;
    private EditText et_password;
    private EditText et_email;
    private Button btn_registered_do;
    //性别
    private  boolean isGender=true;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);

        new TitleBuilder(RegisteredActivty.this).setTitleText("注册")
                .setLeftImage(R.drawable.backicon)
                .setLightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        initView();

    }

    private void initView() {
        et_age=findViewById(R.id.et_age);
        et_desc=findViewById(R.id.et_desc);
        et_username=findViewById(R.id.et_username);
        et_passs=findViewById(R.id.et_pass);
        et_password=findViewById(R.id.et_password);
        et_email=findViewById(R.id.et_email);
        mRadioGroup=findViewById(R.id.mRadioGroup);
        btn_registered_do=findViewById(R.id.btn_registered_do);

        btn_registered_do.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_registered_do:
                //获取输入框的值
                String name=et_username.getText().toString().trim();
                String age=et_age.getText().toString().trim();
                String desc=et_desc.getText().toString().trim();
                String pass=et_passs.getText().toString().trim();
                String password=et_password.getText().toString().trim();
                String email=et_email.getText().toString().trim();

                //判断是否为空
                if(!TextUtils.isEmpty(name)&!TextUtils.isEmpty(age)&
                        !TextUtils.isEmpty(email)&
                        !TextUtils.isEmpty(pass)&
                        !TextUtils.isEmpty(password)){
                    //判断两次输入的密码是否一致
                    if(pass.equals(password)){

                        //先把性别处理一下
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                if(checkedId==R.id.rb_boy){
                                    isGender=true;
                                }else {
                                    isGender=false;
                                }
                            }
                        });
                        //判断简介是否为空
                        if(TextUtils.isEmpty(desc)){
                            desc="这个人很懒，什么也没有留下";
                        }
                        //注册
                        Myuser user=new Myuser();
                        user.setAge(Integer.parseInt(age));
                        user.setDesc(desc);
                        user.setUsername(name);
                        user.setPassword(password);
                        user.setEmail(email);
                        user.setSex(isGender);

                        user.signUp(new SaveListener<Myuser>() {
                            @Override
                            public void done(Myuser myuser, BmobException e) {
                                if(e==null){
                                    Toast.makeText(RegisteredActivty.this,"注册成功！",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(RegisteredActivty.this,"注册失败！"+e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }else {
                        Toast.makeText(this,"两次输入的密码不一致！",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


}
