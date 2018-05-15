package online.icording.smartbulter2_01.utils;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * 项目名     SmartBulter2.01
 * 包名      online.icording.smartbulter2_01.utils
 * 创建时间   2018/5/6 0006 22:53
 * 创建者    yangtaoyao
 * 描述     实现在Fragment中设置RecyclerView点击事件
 * 我们利用了onTouchListener接口对事件进行了拦截，在拦截中处理我们的点击事件，实现了与适配器的解耦，
 * 但是复杂程度会比方法一大。总地来说，如果RecyclerView需要处理的点击事件逻辑很简单，那么可以使用方法一；
 * 如果需要处理比较复杂的点击事件，比如说，双击、长按等点击事件，则需要使用方法二去实现各种复杂的逻辑。
 * 对方法二的优化在实现方法二的RecyclerViewClickListener的时候，
 * 在内部对事件的实现了单击、长按的判断，但是这个长按事件不是标准的，只有松开手指的时候才会触发长按事件，
 * 这也算是一点瑕疵，同时如果要增加别的事件，比如说双击事件，则需要增加相应的逻辑，如果需要判断的事件种类变多则会给我
 * 们的代码编写带来困难，那么有没有更加简便的方法呢？其实安卓SDK为我们提供了一个手势检测类：GestureDetector来处理各种
 * 不同的手势，那么我们完全可以利用GestureDetector来对方法二进行改进。
 **/
public class RecyclerViewClickListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector mGestureDetector;
    private OnItemClickListener mListener;

    //内部接口，定义点击方法以及长按方法
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public RecyclerViewClickListener(Context context, final RecyclerView recyclerView,OnItemClickListener listener){
        mListener = listener;
        mGestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener(){ //这里选择SimpleOnGestureListener实现类，可以根据需要选择重写的方法
                    //单击事件
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                        if(childView != null && mListener != null){
                            mListener.onItemClick(childView,recyclerView.getChildLayoutPosition(childView));
                            return true;
                        }
                        return false;
                    }
                    //长按事件
                    @Override
                    public void onLongPress(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                        if(childView != null && mListener != null){
                            mListener.onItemLongClick(childView,recyclerView.getChildLayoutPosition(childView));
                        }
                    }
                });
    }
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        //把事件交给GestureDetector处理
        if(mGestureDetector.onTouchEvent(e)){
            return true;
        }else
            return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}
