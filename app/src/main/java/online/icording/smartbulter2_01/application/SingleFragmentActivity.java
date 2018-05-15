package online.icording.smartbulter2_01.application;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import online.icording.smartbulter2_01.R;

/**
 * 项目名     SmartBulter2.01
 * 包名      online.icording.smartbulter2_01.application
 * 创建时间   2018/5/7 0007 0:04
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public abstract class SingleFragmentActivity extends FragmentActivity {
    public abstract Fragment createFragment();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_movie_detail_container);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_movie_detail_container, fragment)
                    .commit();
        }
    }

}
