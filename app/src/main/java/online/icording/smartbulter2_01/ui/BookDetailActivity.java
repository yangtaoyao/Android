package online.icording.smartbulter2_01.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.application.SingleFragmentActivity;
import online.icording.smartbulter2_01.entity.BookData;
import online.icording.smartbulter2_01.fragment.BookDetailFragment;

/**
 * 项目名     SmartBulter2.01
 * 包名      online.icording.smartbulter2_01.ui
 * 创建时间   2018/5/6 0006 21:52
 * 创建者    yangtaoyao
 * 描述      图书详情activity
 **/
public class BookDetailActivity extends BaseActivity {

    public static final String EXTRA_BOOK_ID =
            "bookdetailactivit";

    public static Intent newIntent(Context packageContext, BookData bookData) {
        Intent intent = new Intent(packageContext, BookDetailActivity.class);
        intent.putExtra(EXTRA_BOOK_ID, bookData);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_movie_detail_container);
        if (fragment == null) {
            fragment = new BookDetailFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_movie_detail_container, fragment)
                    .commit();
        }
    }

}
