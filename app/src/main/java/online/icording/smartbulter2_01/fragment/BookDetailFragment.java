package online.icording.smartbulter2_01.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.entity.BookData;
import online.icording.smartbulter2_01.ui.BookDetailActivity;
import online.icording.smartbulter2_01.ui.WebViewActivity;
import online.icording.smartbulter2_01.view.CustomPopWindow;
import online.icording.smartbulter2_01.view.TitleBuilder;

/**
 * 项目名     SmartBulter2.01
 * 包名      online.icording.smartbulter2_01.fragment
 * 创建时间   2018/5/6 0006 22:04
 * 创建者    yangtaoyao
 * 描述      图书详情fragment
 **/
public class BookDetailFragment extends Fragment implements View.OnClickListener {

    private CustomPopWindow mCustomPopWindow;

    //


    private TextView title;
    private TextView reading;
    private TextView catalog;
    private TextView online;
    private TextView sub1;
    private TextView sub2;
    public ImageView image;

    private static final String ARG_CRIME_ID = "crime_id";
    private BookData mBookData;

    public static BookDetailFragment newInstance(String tvtitle) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, tvtitle);
        BookDetailFragment fragment = new BookDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBookData = (BookData) getActivity().getIntent()
                .getSerializableExtra(BookDetailActivity.EXTRA_BOOK_ID);
    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_detail, container, false);

        new TitleBuilder(v)
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
                        Intent intent_book_more_detail=new Intent(getActivity(),WebViewActivity.class);
                        intent_book_more_detail.putExtra("title", mBookData.getTitle());
                        intent_book_more_detail.putExtra("url", mBookData.getOnline());
                        startActivity(intent_book_more_detail);
                        break;
                }

            }
        };
        contentView.findViewById(R.id.movie_more_detail).setOnClickListener(listener);

    }
    //

    private void initView(View view) {
        title=view.findViewById(R.id.title);
        catalog=view.findViewById(R.id.catalog);
        reading=view.findViewById(R.id.reading);
        sub1=view.findViewById(R.id.sub1);
        sub2=view.findViewById(R.id.sub2);
        image=view.findViewById(R.id.img);
        image.setOnClickListener(this);

        title.setText(mBookData.getTitle());
        catalog.setText(mBookData.getCatalog());
        reading.setText(mBookData.getReading());
        sub1.setText(mBookData.getSub1());
        sub2.setText(mBookData.getSub2());

        //加载图片
        Glide.with(getContext())
                .load(mBookData.getImg())
                .override(280,500)
                .into(image);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img:
                Intent intent_book_more_detail=new Intent(getActivity(),WebViewActivity.class);
                intent_book_more_detail.putExtra("title", mBookData.getTitle());
                intent_book_more_detail.putExtra("url", mBookData.getImg());
                startActivity(intent_book_more_detail);
        }
    }
}

