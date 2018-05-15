package online.icording.smartbulter2_01.adapter.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.entity.TopNewsData;

/**
 * 项目名     RecyclerView5_2
 * 包名      online.icording.recyclerview5_2
 * 创建时间   2018/5/2 0002 9:57
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<TopNewsData> mList=new ArrayList<>();

    public NewsAdapter(Context context) {
        mLayoutInflater=LayoutInflater.from(context);
    }

    public void addList(List<TopNewsData> list){
        mList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        switch (viewType){
            case TopNewsData.TYPE_ONE:
                return new TypeOneViewHolder(mLayoutInflater.inflate(R.layout.news_item_01,
                        parent, false));
            case TopNewsData.TYPE_TWO:
                return new TypeTwoViewHolder(mLayoutInflater.inflate(R.layout.news_item_02,
                        parent,false));
            case TopNewsData.TYPE_THREE:
                return new TypeThreeViewHolder(mLayoutInflater.inflate(R.layout.news_item_03,
                        parent,false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {
        ((TypeAbstractViewHolder)holder).bindHolder(mList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
            return mList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
