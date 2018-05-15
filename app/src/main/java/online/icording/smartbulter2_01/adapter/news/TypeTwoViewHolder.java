package online.icording.smartbulter2_01.adapter.news;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.entity.TopNewsData;

/**
 * 项目名     RecyclerView5_2
 * 包名      online.icording.recyclerview5_2
 * 创建时间   2018/5/2 0002 10:31
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class TypeTwoViewHolder extends TypeAbstractViewHolder{

    public ImageView weChat_img;
    public TextView weChat_title;
    public TextView wechat_source;
    public TextView wechat_date;
    private Context mContext;

    public TypeTwoViewHolder(View itemView){
        super(itemView);
        weChat_img=itemView.findViewById(R.id.weChat_img);
        weChat_title=itemView.findViewById(R.id.weChat_title);
        wechat_source=itemView.findViewById(R.id.wechat_source);
        wechat_date=itemView.findViewById(R.id.wechat_date);
        mContext=itemView.getContext();
    }
    @Override
    public void bindHolder(TopNewsData model){
        weChat_title.setText(model.getTitle());
        wechat_source.setText(model.getSource());
        wechat_date.setText(model.getWechat_date());
        Glide.with(mContext)
                .load(model.getImgUrl())
                .override(1000,1000)
                .into(weChat_img);
    }
}
