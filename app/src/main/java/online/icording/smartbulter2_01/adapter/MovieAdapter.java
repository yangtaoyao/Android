package online.icording.smartbulter2_01.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import online.icording.smartbulter2_01.MainActivity;
import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.entity.movie.MovieData;
import online.icording.smartbulter2_01.ui.MovieDetailActivity;

import static android.app.PendingIntent.getActivity;
import static android.support.v4.content.ContextCompat.startActivity;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.adapter
 * 创建时间   2018/4/22 0022 0:27
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<MovieData> mDataList=new ArrayList<>();
    //private MovieData data;



    public MovieAdapter(Context context,List<MovieData> mList){
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
            viewHolder=new ViewHolder(mLayoutInflater.inflate(R.layout.movie_item,
                    parent, false));

//        switch (viewType){
//            case 1:
//                return new ViewHolder(mLayoutInflater.inflate(R.layout.movie_item,
//                        parent, false));
//            case 2:
//                return new titleHolder(mLayoutInflater.inflate(R.layout.movie_item_title_one,
//                        parent,false));
//            case 3:
//                return new titleHolder02(mLayoutInflater.inflate(R.layout.movie_item_title_two,
//                        parent,false));
//        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {

        //((ViewHolder) holder).tvTitle.setText(mDataList.get(position).getTvTitle());
        //((RecyclerView.ViewHolder)holder).bindHolder(mDataList.get(position));
        holder.tvTitle.setText(mDataList.get(position).getTvTitle());
        holder.director.setText(mDataList.get(position).getDirector());
        holder.star.setText(mDataList.get(position).getStar());
        holder.grade.setText(mDataList.get(position).getGrade());
        holder.type.setText(mDataList.get(position).getType());
        //
        //加载图片
        Glide.with(mContext)
                .load(mDataList.get(position).getImgUrl())
                .override(220,400)
                .into(holder.image);

        return;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).getItem_type();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder  {

        private TextView tvTitle;
        private TextView director;
        private TextView star;
        private TextView grade;
        private TextView type;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            director = itemView.findViewById(R.id.director);
            star = itemView.findViewById(R.id.star);
            grade = itemView.findViewById(R.id.grade);
            type = itemView.findViewById(R.id.type);
            image = itemView.findViewById(R.id.movie_image);
            //itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(getActivity(),MovieDetailActivity.class);
//            startActivity(intent);
//        }
        //
//        public void bindHolder(MovieData model) {
//            tvTitle.setText(model.getTvTitle());
//            director.setText(model.getDirector());
//            star.setText(model.getStar());
//            grade.setText(model.getGrade());
//            type.setText(model.getType());
//        }
    }
}
