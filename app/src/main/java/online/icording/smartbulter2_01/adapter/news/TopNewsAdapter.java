package online.icording.smartbulter2_01.adapter.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.List;

import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.entity.TopNewsData;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.adapter
 * 创建时间   2018/4/22 0022 0:27
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class TopNewsAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<TopNewsData> mList;
    private TopNewsData data;

    public TopNewsAdapter(Context mContext, List<TopNewsData> mList){
        this.mContext=mContext;
        this.mList=mList;
        inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.news_item_01,null);
            viewHolder.weChat_img=(ImageView) convertView.findViewById(R.id.weChat_img);
            viewHolder.weChat_title=(TextView) convertView.findViewById(R.id.weChat_title);
            viewHolder.wechat_source=(TextView) convertView.findViewById(R.id.wechat_source);
            viewHolder.wechat_date=(TextView)convertView.findViewById(R.id.wechat_date);

            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        data=mList.get(position);
        viewHolder.wechat_source.setText(data.getSource());
        viewHolder.weChat_title.setText(data.getTitle());
        viewHolder.wechat_date.setText(data.getWechat_date());

        //加载图片
        Glide.with(mContext)
                .load(data.getImgUrl())
                .override(1000,1000)
                .into(viewHolder.weChat_img);

        return convertView;
    }

    class ViewHolder{
        private ImageView weChat_img;
        private TextView weChat_title;
        private TextView wechat_source;
        private TextView wechat_date;
    }
}
