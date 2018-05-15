package online.icording.smartbulter2_01.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.entity.TopNewsData;
import online.icording.smartbulter2_01.entity.ZhiHuData;
import online.icording.smartbulter2_01.fragment.ZhihuFragment;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.adapter
 * 创建时间   2018/4/22 0022 0:27
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class ZhiHuAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private ZhiHuData data;
    private List<ZhiHuData> mZhihuList=new ArrayList<>();

    public ZhiHuAdapter(Context mContext, List<ZhiHuData> mZhihuList){
        this.mContext=mContext;
        this.mZhihuList=mZhihuList;
        inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return mZhihuList.size();
    }

    @Override
    public Object getItem(int position) {
        return mZhihuList.get(position);
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
            convertView=inflater.inflate(R.layout.zhihu_item,null);

            viewHolder.zhihu_img=(ImageView) convertView.findViewById(R.id.zhihu_img);
            viewHolder.zhihu_title=(TextView) convertView.findViewById(R.id.zhihu_title);


            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        data=mZhihuList.get(position);
        viewHolder.zhihu_title.setText(data.getTitle());

        //加载图片
        Glide.with(mContext)
                .load(data.getImgUrl())
                .override(200,200)
                .into(viewHolder.zhihu_img);

        return convertView;
    }

    class ViewHolder{
        private ImageView zhihu_img;
        private TextView zhihu_title;
    }
}
