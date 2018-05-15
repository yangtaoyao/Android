package online.icording.smartbulter2_01.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import online.icording.smartbulter2_01.R;

/**
 * 项目名     SmartBulter1.01
 * 包名      online.icording.smartbulter02
 * 创建时间   2018/4/25 0025 11:42
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class DoubanFragment extends Fragment implements View.OnClickListener {

    private DrawerLayout mdrawerlayout;
    private View rootView;
    //TabLayout
    private TabLayout mTabLayout;
    //ViewPager
    private ViewPager mViewPager;
    //Fragment
    private List<Fragment> mFragment;
    //
    private List<String> mTitle;

    private ImageView douban_drawer;


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        //避免重复加载UI
        if(rootView==null){
            rootView=inflater.inflate(R.layout.fragment_douban,null);
            initData();
            findView(rootView);
        }
        ViewGroup parent= (ViewGroup) rootView.getParent();
        if (parent!=null){
            parent.removeView(rootView);
        }
        return rootView;
    }


    private void initData() {
        mTitle=new ArrayList<>();
        mTitle.add("电影");
        mTitle.add("图书");
        mFragment=new ArrayList<>();
        mFragment.add(new MovieFragment());
        mFragment.add(new BooksFragment());


    }

    //初始化View
    private void findView(View view){
        mdrawerlayout=getActivity().findViewById(R.id.drawerlayout);

        mTabLayout=view.findViewById(R.id.douban_mTabLayout);
        //
        mViewPager=(ViewPager) view.findViewById(R.id.douban_mViewPage);

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
//            @Override
//            public CharSequence getPageTitle(int position) {
//
//                return mTitle.get(position);
//            }
        });
        //绑定
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setIcon(R.drawable.selector_icon_move);
        mTabLayout.getTabAt(1).setIcon(R.drawable.selector_icon_book);

        //
        douban_drawer=rootView.findViewById(R.id. douban_drawer);
        douban_drawer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.douban_drawer:
                    mdrawerlayout.openDrawer(Gravity.LEFT);
                    break;
        }
    }
}
