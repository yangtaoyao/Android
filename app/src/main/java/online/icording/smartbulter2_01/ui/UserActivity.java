package online.icording.smartbulter2_01.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.update.UpdateDialogActivity;
import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.entity.Myuser;
import online.icording.smartbulter2_01.entity.TopNewsData;
import online.icording.smartbulter2_01.view.TitleBuilder;

/**
 * 项目名     SmartBulter2.01
 * 包名      online.icording.smartbulter2_01
 * 创建时间   2018/5/10 0010 22:17
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class UserActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_editInfo;
    private Button btn_saveInfo;
    private EditText et_username;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_desc;
    private TextView tv_username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        new TitleBuilder(this).setBgRes(R.color.colorTransparent)
                .setTitleText("个人中心")
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
        et_sex=findViewById(R.id.et_sex);
        et_username=findViewById(R.id.et_username);
        btn_editInfo=findViewById(R.id.btn_editInfo);
        btn_saveInfo=findViewById(R.id.btn_saveInfo);
        tv_username=findViewById(R.id.tv_username);

        et_username.setEnabled(false);
        et_sex.setEnabled(false);
        et_desc.setEnabled(false);
        et_age.setEnabled(false);

        btn_saveInfo.setOnClickListener(this);
        btn_editInfo.setOnClickListener(this);

        //设置具体值
        Myuser userInfo= BmobUser.getCurrentUser(Myuser.class);
        tv_username.setText(userInfo.getUsername());
        et_username.setText(userInfo.getUsername());
        et_desc.setText(userInfo.getDesc());
        et_sex.setText(userInfo.isSex()?"男":"女");
        et_age.setText(userInfo.getAge()+"");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_editInfo:
                et_username.setEnabled(true);
                et_sex.setEnabled(true);
                et_desc.setEnabled(true);
                et_age.setEnabled(true);
                btn_saveInfo.setVisibility(View.VISIBLE);
                btn_editInfo.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_saveInfo:
                String username=et_username.getText().toString();
                String sex=et_sex.getText().toString();
                String age=et_age.getText().toString();
                String desc=et_desc.getText().toString();

                if (!TextUtils.isEmpty(username)&!TextUtils.isEmpty(sex)&!TextUtils.isEmpty(age)){
                    Myuser user=new Myuser();
                    user.setAge(Integer.parseInt(age));
                    user.setUsername(username);
                    if(sex.equals("男")){
                        user.setSex(true);
                    }else if(sex.equals("女")){
                        user.setSex(false);
                    }
                    if(!TextUtils.isEmpty(desc)){
                        user.setDesc(desc);
                    }else {
                        user.setDesc("这个人很懒，什么都没有留下");
                        //et_desc.setText("这个人很懒，什么都没有留下");
                    }
                    //更新资料
                    BmobUser bmobUser=BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                //修改成功
                                et_username.setEnabled(false);
                                et_sex.setEnabled(false);
                                et_desc.setEnabled(false);
                                et_age.setEnabled(false);

                                btn_saveInfo.setVisibility(View.INVISIBLE);
                                btn_editInfo.setVisibility(View.VISIBLE);
                                Toast.makeText(UserActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(UserActivity.this,"修改失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(UserActivity.this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
