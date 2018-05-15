package online.icording.smartbulter2_01.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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

import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.adapter.BooksAdapter;
import online.icording.smartbulter2_01.entity.BookData;
import online.icording.smartbulter2_01.ui.BookDetailActivity;
import online.icording.smartbulter2_01.utils.L;
import online.icording.smartbulter2_01.utils.RecyclerViewClickListener;

import static online.icording.smartbulter2_01.ui.BookDetailActivity.EXTRA_BOOK_ID;

/**
 * 项目名     SmartBulter2.01
 * 包名      online.icording.smartbulter2_01.fragment
 * 创建时间   2018/5/4 0004 23:01
 * 创建者    yangtaoyao
 * 描述      图书列表
 **/
public class BooksFragment extends android.support.v4.app.Fragment {
    private RecyclerView mRecyclerView;
    private List<BookData> mDataList=new ArrayList<>();
    private List<String> mListTitle=new ArrayList<>();
    private List<String> mListUrl=new ArrayList<>();

    private SwipeRefreshLayout swiper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_books,null);


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
        mRecyclerView=view.findViewById(R.id.book_recyclerView);

        //聚合数据图书API 小说
        String url="http://apis.juhe.cn/goodbook/query?catalog_id=247&pn=0&rn=30&dtype=&key=91bc0d17a562ec5029e97f331619785d";

        //
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingJson(t);
                L.i(t);
            }
        });


        //点击事件
        mRecyclerView.addOnItemTouchListener(new RecyclerViewClickListener(getContext(), mRecyclerView,
                new RecyclerViewClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Toast.makeText(getContext(),"Click "+mDataList.get(position).getTitle(),Toast.LENGTH_SHORT).show();

                        //点击打开详情页
                        Intent intent = BookDetailActivity.newIntent(getActivity(), mDataList.get(position));
                        startActivity(intent);
                    }
                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(getContext(),"Long Click "+mDataList.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                    }
                }));
    }



    //解析json
    private void parsingJson(String t){
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONObject jsonResult=jsonObject.getJSONObject("result");
            JSONArray jsonData=jsonResult.getJSONArray("data");

            for(int i=0;i<jsonData.length();i++){
                JSONObject jsonBook= (JSONObject) jsonData.get(i);
                BookData data=new BookData();

                data.setCatalog(jsonBook.getString("catalog"));
                data.setImg(jsonBook.getString("img"));

                String str=jsonBook.getString("online");
                String[] strs=str.split(" ");
                //L.i(strs[0].toString());
                String[] online01=strs[0].split(":");

                String online="";
                for(int n=1;n<online01.length;n++){
                    if(n==1){
                        online=online+online01[n].toString()+":";
                        continue;
                    }
                    online=online+online01[n].toString();
                }
                L.i("链接："+online.toString());
                data.setOnline(online.toString());


                data.setReading(jsonBook.getString("reading"));
                data.setTags(jsonBook.getString("tags"));
                data.setSub1(jsonBook.getString("sub1"));
                data.setSub2(jsonBook.getString("sub2"));
                data.setTitle(jsonBook.getString("title"));

                mDataList.add(data);
                mListTitle.add(data.getTitle());
                mListUrl.add(data.getOnline());
            }
            BooksAdapter adapter=new BooksAdapter(getActivity(),mDataList);
            mRecyclerView.setAdapter(adapter);
            GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),3);
            mRecyclerView.setLayoutManager(gridLayoutManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
