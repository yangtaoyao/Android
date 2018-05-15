package online.icording.smartbulter2_01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;
import online.icording.smartbulter2_01.entity.Myuser;
import online.icording.smartbulter2_01.entity.Tab;
import online.icording.smartbulter2_01.fragment.ChatFragment;
import online.icording.smartbulter2_01.fragment.NewsFragment;
import online.icording.smartbulter2_01.fragment.DoubanFragment;
import online.icording.smartbulter2_01.fragment.HomeFragment;
import online.icording.smartbulter2_01.ui.LoginActivity;
import online.icording.smartbulter2_01.ui.UserActivity;
import online.icording.smartbulter2_01.utils.L;
import online.icording.smartbulter2_01.utils.ShareUtils;
import online.icording.smartbulter2_01.utils.StaticClass;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static boolean isExit=false;
    private FragmentTabHost mTabHost;
    private LayoutInflater mInflater;
    private TextView text;
    private ImageView img;

    private View rootView;

    private List<Tab> mTabs=new ArrayList<>();

    //drawerlayout
    private DrawerLayout mDrawerLayout;
    private LinearLayout exit_app;
    private RelativeLayout drawerlayout_top;
    private TextView btn_login;
    private TextView username;
    private CircleImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(rootView==null){
            rootView=getLayoutInflater().inflate(R.layout.activity_main,null);
        }
        ViewGroup parent= (ViewGroup) rootView.getParent();
        if (parent!=null){
            parent.removeView(rootView);
        }

        initDrawer();
        initTab();
    }

    private void initDrawer() {
        mDrawerLayout=findViewById(R.id.drawerlayout);
        drawerlayout_top=findViewById(R.id.drawerlayout_top);
        exit_app=findViewById(R.id.exit_app);
        btn_login=findViewById(R.id.btn_login);
        username=findViewById(R.id.username);
        profile_image=findViewById(R.id.profile_image);

        exit_app.setOnClickListener(this);
        drawerlayout_top.setOnClickListener(this);

        //侧滑栏登录状态
        isLogin= ShareUtils.getBoolean(MainActivity.this,StaticClass.ALREADY_LOGIN,false);
        if (isLogin){
            Myuser userInfo= BmobUser.getCurrentUser(Myuser.class);
            username.setText(userInfo.getUsername());

            username.setVisibility(View.VISIBLE);
            exit_app.setVisibility(View.VISIBLE);
            profile_image.setVisibility(View.VISIBLE);
            btn_login.setVisibility(View.INVISIBLE);
        }else {
            username.setVisibility(View.INVISIBLE);
            profile_image.setVisibility(View.INVISIBLE);
            btn_login.setVisibility(View.VISIBLE);
            exit_app.setVisibility(View.INVISIBLE);
        }
    }

    private void initTab() {

        //虽然说要简便，不过该少的还是不能少的哈，最少你得创建这5个tab吧
        Tab chat = new Tab(R.string.chat,R.drawable.selector_icon_chat,ChatFragment.class);
        Tab news = new Tab(R.string.news,R.drawable.selector_icon_news, NewsFragment.class);
        Tab tool = new Tab(R.string.find,R.drawable.selector_icon_find, DoubanFragment.class);
        Tab home = new Tab(R.string.home,R.drawable.selector_icon_home,HomeFragment.class);

        //mTabs是一个list数组，需要存放我们创建的五个tab
        mTabs.add(home);
        mTabs.add(news);
        mTabs.add(tool);
        mTabs.add(chat);

        mInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        //调用setup（）方法
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);


        //遍历这个数组，把每个都设置好，
        for (Tab tab:mTabs) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(builderIndiator(tab));
            mTabHost.addTab(tabSpec,tab.getFragment(),null);
        }

        //去掉分割线
        //mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabHost.setCurrentTab(0);
    }

    //既然说到了代码良好，好看，那么我们就重新创建一个方法
    private View builderIndiator(Tab tab){
        View view = mInflater.inflate(R.layout.tab_indicator, null);

        img = (ImageView) view.findViewById(R.id.icon_tab);
        text = (TextView) view.findViewById(R.id.text_indicator);
        img.setBackgroundResource(tab.getImage());
        text.setText(tab.getTitle());
        return view;
    }

    //退出提示
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        //虚拟Menu键设置
        if(keyCode==KeyEvent.KEYCODE_MENU){
            if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }else {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean getIsLogin() {
        return isLogin;
    }
    public void setLogin(boolean login) {
        isLogin = login;
    }
    public boolean isLogin;

    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit=false;
            isLogin= ShareUtils.getBoolean(MainActivity.this,StaticClass.ALREADY_LOGIN,false);
        }
    };

    private void exit() {
        //侧滑栏判断
        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }else {
            if(!isExit){
                isExit=true;
                Toast.makeText(getApplicationContext(),"再按一次退出应用",Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessageDelayed(0,2000);
            }else {
                finish();
            }
        }

    }

    //退出登录
    private void unLogin(){
        Myuser.logOut();
        BmobUser currentUser=Myuser.getCurrentUser();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exit_app:
                //退出登录
                ShareUtils.putBoolean(MainActivity.this,StaticClass.ALREADY_LOGIN,false);
                //
                username.setVisibility(View.INVISIBLE);
                profile_image.setVisibility(View.INVISIBLE);
                btn_login.setVisibility(View.VISIBLE);
                exit_app.setVisibility(View.INVISIBLE);
                unLogin();
                break;
            case R.id.drawerlayout_top:
                //是否登录
                isLogin= ShareUtils.getBoolean(MainActivity.this,StaticClass.ALREADY_LOGIN,false);
                if (isLogin){
                    startActivity(new Intent(MainActivity.this,UserActivity.class));

                }else {
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
                break;
        }
    }
    //判断登录状态设置侧滑栏头像显示与隐藏
    @Override
    protected void onStart() {
        super.onStart();
        isLogin= ShareUtils.getBoolean(MainActivity.this,StaticClass.ALREADY_LOGIN,false);
        if (isLogin){
            username.setVisibility(View.VISIBLE);
            profile_image.setVisibility(View.VISIBLE);
            btn_login.setVisibility(View.INVISIBLE);
            exit_app.setVisibility(View.VISIBLE);
        }else {
            username.setVisibility(View.INVISIBLE);
            profile_image.setVisibility(View.INVISIBLE);
            btn_login.setVisibility(View.VISIBLE);
            exit_app.setVisibility(View.INVISIBLE);
        }
    }
}
