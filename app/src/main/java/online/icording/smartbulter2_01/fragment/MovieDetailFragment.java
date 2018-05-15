package online.icording.smartbulter2_01.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.entity.movie.MovieData;
import online.icording.smartbulter2_01.ui.MovieDetailActivity;
import online.icording.smartbulter2_01.ui.RegisteredActivty;
import online.icording.smartbulter2_01.ui.WebViewActivity;
import online.icording.smartbulter2_01.ui.YaoActivity;
import online.icording.smartbulter2_01.view.CustomPopWindow;
import online.icording.smartbulter2_01.view.TitleBuilder;

/**
 * 项目名     SmartBulter2.01
 * 包名      online.icording.smartbulter2_01.fragment
 * 创建时间   2018/5/6 0006 22:04
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class MovieDetailFragment extends Fragment implements View.OnClickListener {

    private CustomPopWindow mCustomPopWindow;

    private MovieData mMovieData;

    private TextView story;
    private TextView director;
    private TextView star;
    private TextView grade;
    private TextView type;
    private TextView playDate;
    public ImageView image;

    private TextView director_link;
    private TextView star01_link;
    private TextView star02_link;
    private TextView star03_link;
    private TextView star04_link;

    private static final String ARG_CRIME_ID = "crime_id";

    public static MovieDetailFragment newInstance(String tvtitle) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, tvtitle);
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //
        mMovieData = (MovieData) getActivity().getIntent()
                .getSerializableExtra(MovieDetailActivity.EXTRA_CRIME_ID);
    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        new TitleBuilder(v).setTitleText(mMovieData.getTvTitle())
                .setLeftImage(R.drawable.backicon)
                .setRightImage(R.drawable.switch_)
                .setLightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().finish();
                    }
                })
        .setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.movie_mcustompopwindow,null);
                //处理popWindow 显示内容
                handleLogic(contentView);
                //创建并显示popWindow
                mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(getContext())
                        .setView(contentView)
                        .create();
                mCustomPopWindow .showAsDropDown(v,-150,  -100);
            }
        })
        ;
        initView(v);
        return v;
    }
    //handleLogic右上角点击菜单
    private void handleLogic(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCustomPopWindow!=null){
                    mCustomPopWindow.dissmiss();
                }

                switch (v.getId()){
                    case R.id.movie_more_detail:
                        Intent intent_movie_more_detail=new Intent(getActivity(),WebViewActivity.class);
                        intent_movie_more_detail.putExtra("title",mMovieData.getTvTitle());
                        intent_movie_more_detail.putExtra("url",mMovieData.getUrl());
                        startActivity(intent_movie_more_detail);
                        break;
                }

            }
        };
        contentView.findViewById(R.id.movie_more_detail).setOnClickListener(listener);

    }
    //

    private void initView(View view) {
        story=view.findViewById(R.id.story);
        director = view.findViewById(R.id.director);
        star = view.findViewById(R.id.star);
        grade = view.findViewById(R.id.grade);
        type = view.findViewById(R.id.type);
        image = view.findViewById(R.id.movie_image);
        playDate=view.findViewById(R.id.playDate);

        director_link=view.findViewById(R.id.director_link);
        star01_link=view.findViewById(R.id.star01_link);
        star02_link=view.findViewById(R.id.star02_link);
        star03_link=view.findViewById(R.id.star03_link);
        star04_link=view.findViewById(R.id.star04_link);

        director_link.setText(mMovieData.getDirector());
        star01_link.setText(mMovieData.getStar());
//        star03_link.setText(mMovieData.getStory());
//        star01_link.setText(mMovieData.getStory());
//        star04_link.setText(mMovieData.getStory());

        image.setOnClickListener(this);
        director_link.setOnClickListener(this);
        star02_link.setOnClickListener(this);
        star03_link.setOnClickListener(this);
        star01_link.setOnClickListener(this);
        star04_link.setOnClickListener(this);

        story.setText(mMovieData.getStory());
        director.setText(mMovieData.getDirector());
        star.setText(mMovieData.getStar());
        grade.setText(mMovieData.getGrade());
        type.setText(mMovieData.getType());
        playDate.setText(mMovieData.getPlayDate());
        //加载图片
        Glide.with(getContext())
                .load(mMovieData.getImgUrl())
                .override(280,500)
                .into(image);
        //


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.director_link:
                Intent intent0=new Intent(getActivity(),WebViewActivity.class);
                intent0.putExtra("title",mMovieData.getDirector());
                intent0.putExtra("url",mMovieData.getDirector_link());
                startActivity(intent0);
                break;
            case R.id.star01_link:
                Intent intent1=new Intent(getActivity(),WebViewActivity.class);
                intent1.putExtra("title",mMovieData.getStar());
                intent1.putExtra("url",mMovieData.getUrl());
                startActivity(intent1);
                break;
            case R.id.movie_image:
                Intent intent2=new Intent(getActivity(),WebViewActivity.class);
                intent2.putExtra("title",mMovieData.getTvTitle());
                intent2.putExtra("url",mMovieData.getImgUrl());
                startActivity(intent2);
                break;
            case R.id.star03_link:
                Intent intent3=new Intent(getActivity(),WebViewActivity.class);
                intent3.putExtra("title",mMovieData.getDirector());
                intent3.putExtra("url",mMovieData.getImgUrl());
                startActivity(intent3);
                break;
            case R.id.star04_link:
                Intent intent4=new Intent(getActivity(),WebViewActivity.class);
                intent4.putExtra("title",mMovieData.getDirector());
                intent4.putExtra("url",mMovieData.getImgUrl());
                startActivity(intent4);
                break;
        }
    }
}

