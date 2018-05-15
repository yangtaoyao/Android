package online.icording.smartbulter2_01.fragment.news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.adapter.news.TopNewsAdapter;
import online.icording.smartbulter2_01.entity.TopNewsData;
import online.icording.smartbulter2_01.ui.WebViewActivity;
import online.icording.smartbulter2_01.utils.L;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopnewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopnewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopnewsFragment extends Fragment {

    private ListView mListView;
    private List<TopNewsData> mList=new ArrayList<>();
    private List<String> mListTitle=new ArrayList<>();
    private List<String> mListUrl=new ArrayList<>();

    private SwipeRefreshLayout swiper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_topnews,null);


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
        mListView=view.findViewById(R.id.mListView);

        String url="http://api.avatardata.cn/Top/News?key=";

        //新闻头条
        RxVolley.get("http://api.avatardata.cn/TouTiao/Query?key=1dac986b64174735b6050fcb7ad0f560&type=junshi", new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingJson(t);

            }
        });

        //点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                L.i("position"+position);
                //打开weview传递参数
                Intent intent=new Intent(getActivity(),WebViewActivity.class);
                intent.putExtra("title",mListTitle.get(position));
                intent.putExtra("url",mListUrl.get(position));

                startActivity(intent);
            }
        });
    }

    //解析json
    private void parsingJson(String t){
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONObject jsonResult=jsonObject.getJSONObject("result");
            JSONArray jsonList=jsonResult.getJSONArray("data");
            for(int i=0;i<jsonList.length();i++){
                JSONObject json=(JSONObject)jsonList.get(i);
                TopNewsData data=new TopNewsData();

                String url=json.getString("url");
                String title=json.getString("title");

                data.setWechat_date(json.getString("date"));
                data.setTitle(title);
                data.setSource(json.getString("author_name"));
                data.setImgUrl(json.getString("thumbnail_pic_s"));
                data.setNewUrl(url);

                mListTitle.add(title);
                mListUrl.add(url);
                mList.add(data);

            }
            TopNewsAdapter adapter=new TopNewsAdapter(getActivity(),mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
