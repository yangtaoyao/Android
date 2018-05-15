package online.icording.smartbulter2_01.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.fragment.news.GuoJiFragment;
import online.icording.smartbulter2_01.fragment.news.GuoneiFragment;
import online.icording.smartbulter2_01.fragment.news.ShehuiFragment;
import online.icording.smartbulter2_01.fragment.news.TopFragment;
import online.icording.smartbulter2_01.fragment.news.TopnewsFragment;
import online.icording.smartbulter2_01.ui.YaoActivity;
import online.icording.smartbulter2_01.view.CustomPopWindow;
import online.icording.smartbulter2_01.view.TitleBuilder;

/**
 * 项目名     SmartBulter1.01
 * 包名      online.icording.smartbulter.fragment
 * 创建时间   2018/4/25 0025 9:37
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class NewsFragment extends Fragment {

    //TabLayout
    private TabLayout mTabLayout;
    //ViewPager
    private ViewPager mViewPager;
    //Fragment
    private List<Fragment> mFragment;
    //Title
    private List<String> mTitle;

    private View rootView;
    //DrawerLayout
    private DrawerLayout mdrawerlayout ;
    private CustomPopWindow mCustomPopWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //避免重复加载UI
        if(rootView==null){
            rootView=inflater.inflate(R.layout.fragment_news,null);

            initData();
            findView(rootView);


            //设置标题栏
            new TitleBuilder(rootView)
                    .setLeftImage(R.drawable.drawer)
                    .setLightOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //mdrawerlayout
                            mdrawerlayout.openDrawer(Gravity.LEFT);
                        }
                    });
            //



        }
        ViewGroup parent= (ViewGroup) rootView.getParent();
        if (parent!=null){
            parent.removeView(rootView);
        }
        return rootView;
    }

    //初始化数据
    private void initData() {
        mTitle=new ArrayList<>();
        mTitle.add("头条");
        mTitle.add("国际");
        mTitle.add("国内");
        mTitle.add("社会");
        mTitle.add("军事");
        mTitle.add("知乎");


        mFragment=new ArrayList<>();

        mFragment.add(new TopFragment());
        mFragment.add(new GuoJiFragment());
        mFragment.add(new GuoneiFragment());
        mFragment.add(new ShehuiFragment());
        mFragment.add(new TopnewsFragment());
        mFragment.add(new ZhihuFragment());
    }

    //初始化View
    private void findView(View view){
        mdrawerlayout=getActivity().findViewById(R.id.drawerlayout);

        mTabLayout=view.findViewById(R.id.mTabLayout);
        mViewPager=(ViewPager) view.findViewById(R.id.mViewPage);

        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());

        //mViewPager滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
            //设置标题
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });

        //绑定
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
