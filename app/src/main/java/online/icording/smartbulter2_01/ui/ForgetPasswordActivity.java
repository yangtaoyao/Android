package online.icording.smartbulter2_01.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.entity.Myuser;
import online.icording.smartbulter2_01.view.TitleBuilder;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.ui
 * 创建时间   2018/4/16 0016 20:08
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_resetpassword;
    private Button btn_findpassword;
    private EditText et_passwordP;
    private EditText et_passwordN1;
    private EditText et_passwordN2;
    private EditText et_email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        new TitleBuilder(ForgetPasswordActivity.this).setTitleText("忘记密码")
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
        btn_findpassword=findViewById(R.id.btn_findpassword);
        btn_resetpassword=findViewById(R.id.btn_resetpassword);
        et_email=findViewById(R.id.et_email);
        et_passwordN1=findViewById(R.id.et_passwordN1);
        et_passwordN2=findViewById(R.id.et_passwordN2);
        et_passwordP=findViewById(R.id.et_passwordP);

        btn_findpassword.setOnClickListener(this);
        btn_resetpassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_findpassword:
                final String email=et_email.getText().toString().trim();
                if(!email.isEmpty()){
                    Myuser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(ForgetPasswordActivity.this,"邮箱发送成功至:"+email,Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ForgetPasswordActivity.this,"邮箱发送失败"+e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_resetpassword:
                String passwordN1=et_passwordN1.getText().toString().trim();
                String passwordN2=et_passwordN2.getText().toString().trim();
                String passwordP=et_passwordP.getText().toString().trim();

                if(!TextUtils.isEmpty(passwordN1)&!TextUtils.isEmpty(passwordN2)&!TextUtils.isEmpty(passwordP)){
                    if(passwordN2.equals(passwordN1)){
                        Myuser.updateCurrentUserPassword(passwordP,passwordN1,new UpdateListener(){
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Toast.makeText(ForgetPasswordActivity.this,"重置密码成功",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(ForgetPasswordActivity.this,"重置密码失败:",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(this,"两次输入的新密码不一致",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
