package online.icording.smartbulter2_01.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import online.icording.smartbulter2_01.adapter.ZhiHuAdapter;
import online.icording.smartbulter2_01.entity.ZhiHuData;
import online.icording.smartbulter2_01.ui.WebViewZhiHuActivity;
import online.icording.smartbulter2_01.view.CustomPopWindow;
import online.icording.smartbulter2_01.view.TitleBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ZhihuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ZhihuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZhihuFragment extends Fragment {

    private ListView mListViewZhihu;
    public List<ZhiHuData> mZhihuList=new ArrayList<>();

    private List<String> mZhihuListTitle=new ArrayList<>();
    private List<String> mZhihuListUrl=new ArrayList<String>();

    String t1;
    private String image_url;
    private String share_url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_zhihu,null);


        findView(view);

        return view;
    }

    //初始化View
    public void findView(View view){
        mListViewZhihu=view.findViewById(R.id.mListViewZhihu);

        String url="https://news-at.zhihu.com/api/3/news/hot";


        //知乎
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //Toast.makeText(getContext(), "json :"+t, Toast.LENGTH_SHORT).show();
                parsingJson(t);
                t1=t;
            }
        });

//        for(int i=0;i<mZhihuList.size();i++){
//            final int finalI = i;
//            RxVolley.get(mZhihuList.get(i).getUrl(), new HttpCallback() {
//                @Override
//                public void onSuccess(String t) {
//                    try {
//                        JSONObject jsonObject=new JSONObject(t);
//                        mZhihuList.get(finalI).setImgUrl(jsonObject.getString("image"));
//                        mZhihuList.get(finalI).setShare_url(jsonObject.getString("share_url"));
//                        mZhihuListUrl.add(jsonObject.getString("share_url"));
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }

        ZhiHuAdapter adapter=new ZhiHuAdapter(getActivity(),mZhihuList);
        mListViewZhihu.setAdapter(adapter);

        //点击事件
        mListViewZhihu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),WebViewZhiHuActivity.class);
                intent.putExtra("title",mZhihuListTitle.get(position));
                intent.putExtra("url",mZhihuListUrl.get(position));

                startActivity(intent);
            }
        });
    }

    //解析json
    private void parsingJson(String t){
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONArray jsonList=jsonObject.getJSONArray("recent");

            for(int i=0;i<jsonList.length();i++){
                JSONObject json=(JSONObject)jsonList.get(i);
                ZhiHuData data=new ZhiHuData();


                data.setUrl(json.getString("url"));
                data.setTitle(json.getString("title"));
//                data.setImgUrl(image_url);
//                data.setNewUrl(share_url);

                mZhihuListTitle.add(json.getString("title"));
                mZhihuListUrl.add(json.getString("url"));
                mZhihuList.add(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
