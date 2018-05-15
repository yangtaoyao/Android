package online.icording.smartbulter2_01.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.xys.libzxing.zxing.activity.CaptureActivity;

import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.ui.BaiduMapActivity;
import online.icording.smartbulter2_01.ui.ComputeActivity;
import online.icording.smartbulter2_01.ui.TranslationActivity;
import online.icording.smartbulter2_01.ui.WebViewActivity;
import online.icording.smartbulter2_01.ui.YaoActivity;
import online.icording.smartbulter2_01.view.CustomPopWindow;
import online.icording.smartbulter2_01.view.TitleBuilder;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private DrawerLayout mdrawerlayout;
    private CustomPopWindow mCustomPopWindow;

    private FragmentTabHost mTabHost;
    private LinearLayout gotoBook;
    private LinearLayout gotoBaiduMap;
    private LinearLayout gotoTranslate;
    private LinearLayout gotoMovie;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //避免重复加载UI
        if(rootView==null){
            rootView=inflater.inflate(R.layout.fragment_home,null);
//            initData();
            findView(rootView);
            //设置标题栏
            new TitleBuilder(rootView)
                    .setLeftImage(R.drawable.drawer)
                    .setRightImage(R.drawable.add)
                    .setLightOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //打开drawerlayout
                            mdrawerlayout.openDrawer(Gravity.LEFT);
                        }
                    })
                    .setRightOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_layout,null);
                            //处理popWindow 显示内容
                            handleLogic(contentView);
                            //创建并显示popWindow
                            mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(getContext())
                                    .setView(contentView)
                                    .create();
                            mCustomPopWindow .showAsDropDown(v,-16,  -20);
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

    private void findView(View rootView) {
        mdrawerlayout=getActivity().findViewById(R.id.drawerlayout);
        mTabHost = getActivity().findViewById(android.R.id.tabhost);

        gotoBook=rootView.findViewById(R.id.goto_book);
        gotoMovie=rootView.findViewById(R.id.goto_movie);
       gotoTranslate=rootView.findViewById(R.id.goto_translate);
        gotoBaiduMap=rootView.findViewById(R.id.goto_baidu_map);

        gotoBook.setOnClickListener(this);
        gotoBaiduMap.setOnClickListener(this);
        gotoMovie.setOnClickListener(this);
       gotoTranslate.setOnClickListener(this);


    }

    private void handleLogic(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCustomPopWindow!=null){
                    mCustomPopWindow.dissmiss();
                }
                switch (v.getId()){
                    case R.id.menu_yao:
                        startActivity(new Intent(getActivity(),YaoActivity.class));
                        break;
                    case R.id.menu_sao:
                        //打开扫描界面扫描条形码或二维码
                         Intent openCameraIntent = new Intent(getActivity(), CaptureActivity.class);
                         startActivityForResult(openCameraIntent, 0);

                        break;

                }

            }
        };
        contentView.findViewById(R.id.menu_yao).setOnClickListener(listener);
        contentView.findViewById(R.id.menu_sao).setOnClickListener(listener);
    }

    //返回扫描结果
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Bundle bundle=data.getExtras();
            String scanResult=bundle.getString("result");
            //Toast.makeText(getContext(),scanResult,Toast.LENGTH_LONG).show();
            //
            Intent intent_movie_more_detail=new Intent(getActivity(),WebViewActivity.class);
            intent_movie_more_detail.putExtra("title","");
            intent_movie_more_detail.putExtra("url",scanResult);
            startActivity(intent_movie_more_detail);
        }
    }

    //按钮跳转指定fragment页面
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goto_translate:
                startActivity(new Intent(getActivity(), TranslationActivity.class));
                break;
            case R.id.goto_baidu_map:
                startActivity(new Intent(getActivity(), BaiduMapActivity.class));
                break;
            case  R.id.goto_movie:
                mTabHost.setCurrentTab(2);
                break;
            case  R.id.goto_book:
                mTabHost.setCurrentTab(2);
                break;
        }
    }
}
