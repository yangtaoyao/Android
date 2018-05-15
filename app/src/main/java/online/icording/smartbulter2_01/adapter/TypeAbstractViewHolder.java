package online.icording.smartbulter2_01.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import online.icording.smartbulter2_01.entity.movie.MovieData;

/**
 * 项目名     RecyclerView5_2
 * 包名      online.icording.recyclerview5_2
 * 创建时间   2018/5/2 0002 10:38
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public abstract class TypeAbstractViewHolder extends RecyclerView.ViewHolder {

    public TypeAbstractViewHolder(View itemView) {
        super(itemView);
    }
    public abstract void bindHolder(MovieData model);

}
