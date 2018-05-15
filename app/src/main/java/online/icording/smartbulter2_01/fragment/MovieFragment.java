package online.icording.smartbulter2_01.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import online.icording.smartbulter2_01.MainActivity;
import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.adapter.MovieAdapter;
import online.icording.smartbulter2_01.entity.movie.MovieData;
import online.icording.smartbulter2_01.ui.MovieDetailActivity;
import online.icording.smartbulter2_01.utils.L;
import online.icording.smartbulter2_01.utils.RecyclerViewClickListener;

/**
 * 项目名     SmartBulter2.01
 * 包名      online.icording.smartbulter2_01.fragment
 * 创建时间   2018/5/4 0004 23:01
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class MovieFragment extends android.support.v4.app.Fragment {
    private RecyclerView mRecyclerView;
    private List<MovieData> mDataList=new ArrayList<>();
    private List<String> mListTitle=new ArrayList<>();
    private List<String> mListUrl=new ArrayList<>();

    private SwipeRefreshLayout swiper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_movie,null);


        //初始化SwipeRefreshLayout
        swiper = (SwipeRefreshLayout) view.findViewById(R.id.id_swipeRefreshLayout);
        //为SwipeRefreshLayout设置监听事件
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //mAdapter.notifyDataSetChanged();
                        //收起刷新图
                        swiper.setRefreshing(false);
                    }
                },3000);
            }
        });
        //为SwipeRefreshLayout设置刷新时的颜色变化，最多可以设置4种
        swiper.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
//
        findView(view);
        return view;
    }

    //初始化View
    public void findView(View view){
        mRecyclerView=view.findViewById(R.id.movie_recyclerView);

        String url="http://op.juhe.cn/onebox/movie/pmovie?key=8bbcbebfdcd13f76d7cf32890c4a0ad6&city=";

        //
        RxVolley.get(url+"北京", new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingJson(t);
                L.i(t);
                //Toast.makeText(getContext(),t,Toast.LENGTH_LONG).show();
            }
        });

        //点击事件
        mRecyclerView.addOnItemTouchListener(new RecyclerViewClickListener(getContext(), mRecyclerView,
                new RecyclerViewClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Toast.makeText(getContext(),"Click "+mDataList.get(position).getTvTitle(),Toast.LENGTH_SHORT).show();
                        //
                        Intent intent = MovieDetailActivity.newIntent(getActivity(), mDataList.get(position));
                        startActivity(intent);
                    }
                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(getContext(),"Long Click "+mDataList.get(position).getTvTitle(),Toast.LENGTH_SHORT).show();
                    }
                }));
    }



    //解析json
    private void parsingJson(String t){
        try {
//            MovieData datatitle01=new MovieData();
//            datatitle01.setItem_type(3);
//            mDataList.add(datatitle01);

            JSONObject jsonObject=new JSONObject(t);
            JSONObject jsonResult=jsonObject.getJSONObject("result");
            JSONArray jsonData=jsonResult.getJSONArray("data");

            //正在热映
            JSONObject jsonHotData=(JSONObject)jsonData.get(0);
            JSONArray jsonMovie=jsonHotData.getJSONArray("data");
            //正在热映的电影
            for(int i=0;i<jsonMovie.length();i++){
                MovieData data=new MovieData();
                JSONObject jsonMovieData=(JSONObject)jsonMovie.get(i);
                //setTvTitle
                data.setTvTitle(jsonMovieData.optString("tvTitle"));
                //setGrade
                data.setGrade(jsonMovieData.optString("grade"));

                //详情URL
                data.setUrl(jsonMovieData.optString("m_iconlinkUrl"));

                //上映日期JSONObject
                JSONObject jsonPlayDate=jsonMovieData.getJSONObject("playDate");
                data.setPlayDate(jsonPlayDate.optString("data"));

                //导演director
                JSONObject jsonDirectorName=((jsonMovieData.getJSONObject("director")).getJSONObject("data")).getJSONObject("1");
                data.setDirector(jsonDirectorName.optString("name"));
                data.setDirector_link((((jsonMovieData.getJSONObject("director")).getJSONObject("data")).getJSONObject("1")).optString("link"));

                //主演
                JSONObject jsonStars=((jsonMovieData.getJSONObject("star")).getJSONObject("data"));
                String stars=jsonStars.getJSONObject("1").optString("name");
                for(int n=2;n<6;n++){
                    //判断jsonObject是否含有”n“ key
                    if (jsonStars.has(n+"")){
                        stars=stars + " / "+jsonStars.getJSONObject(n+"").optString("name");
                    }
                }
                data.setStar(stars);

                //类型
                JSONObject jsonTypes=((jsonMovieData.getJSONObject("type")).getJSONObject("data"));
                String types=jsonTypes.getJSONObject("1").optString("name");
                for(int n=2;n<6;n++){
                    //判断jsonObject是否含有”n“ key
                    if (jsonTypes.has(n+"")){
                        types=types + " / "+jsonTypes.getJSONObject(n+"").optString("name");
                    }
                }
                data.setType(types);

                //剧情
                JSONObject jsonStory=((jsonMovieData.getJSONObject("story")).getJSONObject("data"));
                String storyBrief=jsonStory.optString("storyBrief");
                String storyMoreLink=jsonStory.optString("storyMoreLink");
                data.setStory(storyBrief);

                //图片
                data.setImgUrl(jsonMovieData.optString("iconaddress"));
//                JSONArray jsonMore=((jsonMovieData.getJSONObject("more")).getJSONArray("data"));
//                String imageUrl=((JSONObject)jsonMore.get(1)).getString("link");
//                data.setImgUrl(imageUrl);
//                Toast.makeText(getContext(),"imageurl"+imageUrl,Toast.LENGTH_LONG).show();
                L.i("imageurl:"+ data.getImgUrl());

                data.setItem_type(1);
                mDataList.add(data);
            }

//
            //即将上映
            JSONObject jsonComingData=(JSONObject)jsonData.get(1);
            JSONArray jsonMovie02=jsonComingData.getJSONArray("data");
            //正在热映的电影
            for(int i=0;i<jsonMovie02.length();i++){
                MovieData data=new MovieData();
                JSONObject jsonMovieData=(JSONObject)jsonMovie02.get(i);
                data.setTvTitle(jsonMovieData.getString("tvTitle"));

                //setGrade
                data.setGrade(jsonMovieData.optString("grade"));

                //上映日期JSONObject
                JSONObject jsonPlayDate=jsonMovieData.getJSONObject("playDate");
                data.setPlayDate(jsonPlayDate.optString("data"));

                //导演director
                JSONObject jsonDirectorName=((jsonMovieData.getJSONObject("director")).getJSONObject("data")).getJSONObject("1");
                data.setDirector(jsonDirectorName.optString("name"));
                data.setDirector_link((((jsonMovieData.getJSONObject("director")).getJSONObject("data")).getJSONObject("1")).optString("link"));

                //主演
                JSONObject jsonStars=((jsonMovieData.getJSONObject("star")).getJSONObject("data"));
                String stars=jsonStars.getJSONObject("1").optString("name");
                for(int n=2;n<6;n++){
                    //判断jsonObject是否含有”n“ key
                    if (jsonStars.has(n+"")){
                        stars=stars + " / "+jsonStars.getJSONObject(n+"").optString("name");
                    }
                }
                data.setStar(stars);

                //类型
                JSONObject jsonTypes=((jsonMovieData.getJSONObject("type")).getJSONObject("data"));
                String types=jsonTypes.getJSONObject("1").optString("name");
                for(int n=2;n<6;n++){
                    //判断jsonObject是否含有”n“ key
                    if (jsonTypes.has(n+"")){
                        types=types + " / "+jsonTypes.getJSONObject(n+"").optString("name");
                    }
                }
                data.setType(types);

                //剧情
                JSONObject jsonStory=((jsonMovieData.getJSONObject("story")).getJSONObject("data"));
                String storyBrief=jsonStory.optString("storyBrief");
                String storyMoreLink=jsonStory.optString("storyMoreLink");
                data.setStory(storyBrief);

                //图片
                data.setImgUrl(jsonMovieData.optString("iconaddress"));

                data.setItem_type(1);
                mDataList.add(data);
            }


            MovieAdapter adapter=new MovieAdapter(getActivity(),mDataList);
            mRecyclerView.setAdapter(adapter);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),
                    LinearLayoutManager.VERTICAL,false);
            mRecyclerView.setLayoutManager(linearLayoutManager);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
