package online.icording.smartbulter2_01.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import online.icording.smartbulter2_01.MainActivity;
import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.utils.ShareUtils;
import online.icording.smartbulter2_01.utils.StaticClass;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.ui
 * 创建时间   2018/4/15 0015 10:45
 * 创建者    yangtaoyao
 * 描述      引导页
 **/
public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    //容器
    private List<View>mList=new ArrayList<>();
    private View view1,view2,view3;

    private TextView btn_back;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();

    }

    //初始化页面
    private void initView() {
        mViewPager=findViewById(R.id.mViewPager);

        view1=View.inflate(this,R.layout.pager_item_one,null);
        view2=View.inflate(this,R.layout.pager_item_two,null);
        view3=View.inflate(this,R.layout.pager_item_three,null);

        btn_back=findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        view3.findViewById(R.id.btn_start).setOnClickListener(this);
        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        //设置适配器
        mViewPager.setAdapter(new GuideAdapter());

        //监听ViewPager滑动
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        btn_back.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        btn_back.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        btn_back.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //进入主页按钮点击
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.btn_back:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
    }

    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject( View view,  Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container,int position) {
            ((ViewPager)container).addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem( ViewGroup container, int position,  Object object) {
            ((ViewPager)container).removeView(mList.get(position));
//            super.destroyItem(container, position, object);
        }
    }

}
