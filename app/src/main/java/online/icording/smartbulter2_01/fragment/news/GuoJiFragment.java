package online.icording.smartbulter2_01.fragment.news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.adapter.news.NewsAdapter;
import online.icording.smartbulter2_01.adapter.news.TopNewsAdapter;
import online.icording.smartbulter2_01.entity.TopNewsData;
import online.icording.smartbulter2_01.ui.WebViewActivity;
import online.icording.smartbulter2_01.utils.L;
import online.icording.smartbulter2_01.utils.RecyclerViewClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GuoJiFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GuoJiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuoJiFragment extends Fragment {


    private List<TopNewsData> mList=new ArrayList<>();
    private List<String> mListTitle=new ArrayList<>();
    private List<String> mListUrl=new ArrayList<>();

    private NewsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swiper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_guoji_news,null);


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
        mRecyclerView=view.findViewById(R.id.id_recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));

        mAdapter=new NewsAdapter(getContext());
        //新闻头条
        RxVolley.get("http://api.avatardata.cn/TouTiao/Query?key=1dac986b64174735b6050fcb7ad0f560&type=guoji", new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingJson(t);

            }
        });
        //点击事件
        mRecyclerView.addOnItemTouchListener(new RecyclerViewClickListener(getContext(), mRecyclerView,
                new RecyclerViewClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Toast.makeText(getContext(),"Long Click "+mList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getActivity(),WebViewActivity.class);
                        intent.putExtra("title",mList.get(position).getTitle());
                        intent.putExtra("url",mList.get(position).getNewUrl());
                        startActivity(intent);
                    }
                    @Override
                    public void onItemLongClick(View view, int position) {
                        //Toast.makeText(getContext(),"Long Click "+mList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                    }
                }));
        mRecyclerView.setAdapter(mAdapter);
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
                if(json.has("thumbnail_pic_s02")){
                    data.setImgUrl02(json.getString("thumbnail_pic_s02"));
                }
                if(json.has("thumbnail_pic_s03")){
                    data.setImgUrl02(json.getString("thumbnail_pic_s03"));
                }
                data.setNewUrl(url);

                //type
                if(!data.getImgUrl02().equals("")&&!data.getImgUrl03().equals("")){
                    data.setType(3);
                }else if(i%5==0){
                    data.setType(2);
                }else {
                    data.setType(1);
                }
                mList.add(data);
            }
            mAdapter.addList(mList);
            mAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
