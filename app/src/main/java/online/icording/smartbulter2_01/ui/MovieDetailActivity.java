package online.icording.smartbulter2_01.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.UUID;

import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.application.SingleFragmentActivity;
import online.icording.smartbulter2_01.entity.movie.MovieData;
import online.icording.smartbulter2_01.fragment.MovieDetailFragment;

/**
 * 项目名     SmartBulter2.01
 * 包名      online.icording.smartbulter2_01.ui
 * 创建时间   2018/5/6 0006 21:52
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class MovieDetailActivity extends SingleFragmentActivity {

    public static final String EXTRA_CRIME_ID =
            "com.bignerdranch.android.criminalintent.crime_id";

    public static Intent newIntent(Context packageContext, MovieData movieData) {
        Intent intent = new Intent(packageContext, MovieDetailActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, movieData);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return new MovieDetailFragment();
    }
}
