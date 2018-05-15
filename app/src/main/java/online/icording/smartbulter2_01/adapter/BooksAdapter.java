package online.icording.smartbulter2_01.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.entity.BookData;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.adapter
 * 创建时间   2018/4/22 0022 0:27
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder>{
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<BookData> mDataList=new ArrayList<>();
    //private MovieData data;



    public BooksAdapter(Context context, List<BookData> mList){
        this.mContext=context;
        this.mDataList=mList;
        mLayoutInflater=LayoutInflater.from(context);
        //

        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
//         View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
//        return new ViewHolder(layout);
        ViewHolder viewHolder;
            viewHolder=new ViewHolder(mLayoutInflater.inflate(R.layout.book_item,
                    parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        holder.title.setText(mDataList.get(position).getTitle());
        //加载图片
        Glide.with(mContext)
                .load(mDataList.get(position).getImg())
                .override(220,400)
                .into(holder.img);

        return;
    }

//    @Override
//    public int getItemViewType(int position) {
//        return mDataList.get(position).getItem_type();
//    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder  {

        private TextView title;
        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.book_title);
            img = itemView.findViewById(R.id.book_img);
        }
    }
}
