package online.icording.smartbulter2_01.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.ui
 * 创建时间   2018/4/14 0014 11:52
 * 创建者    yangtaoyao
 * 描述      Activity基类
 *
 * 统一属性
 * 统一接口
 * 统一方法
 **/
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //显示返回键
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //菜单栏操作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
