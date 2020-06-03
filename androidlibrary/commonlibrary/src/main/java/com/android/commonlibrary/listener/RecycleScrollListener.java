package com.android.commonlibrary.listener;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Title:监听Recycle滑动到顶部，底部，向上滑，向下滑
 *
 * Author:pei
 * Date: 2017/7/22
 * Description:
 */
public class RecycleScrollListener extends RecyclerView.OnScrollListener {

    //滑动位置参数
    public static final int SCROLL_TOP=1;//滑动到顶部
    public static final int SCROLL_NO_TB=0;//滑动到既不是顶部也不是底部的位置
    public static final int SCROLL_BOTTOM=-1;//滑动到底部

    private OnScrollStateListener mOnScrollStateListener;//是否在滑动监听
    private OnScrollPositionListener mOnScrollPositionListener;//滑动位置监听
    private OnScrollDirectionListener mOnScrollDirectionListener;//滑动方向监听

    /**设置是否正在滑动的监听**/
    public void setOnScrollStateListener(OnScrollStateListener listener){
        this.mOnScrollStateListener=listener;
    }

    /**设置滑动位置监听**/
    public void setOnScrollPositionListener(OnScrollPositionListener listener){
        this.mOnScrollPositionListener=listener;
    }

    /**设置滑动方向监听**/
    public void setOnScrollDirectionListener(OnScrollDirectionListener listener){
        this.mOnScrollDirectionListener=listener;
    }

    @Override
    public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        //是否在滑动监听
        if(mOnScrollStateListener!=null){
            if(recyclerView.getScrollState()!=0){//recycleView正在滑动
                mOnScrollStateListener.scrollState(true);
            }else{//recycleView未滑动
                mOnScrollStateListener.scrollState(false);
            }
        }

        //滑动位置监听
        if(mOnScrollPositionListener!=null){
            if(!recyclerView.canScrollVertically(-1)){
                //滑动到顶部
                mOnScrollPositionListener.scrollPosition(RecycleScrollListener.SCROLL_TOP);
            }else if(!recyclerView.canScrollVertically(1)){
                //滑动到底部
                mOnScrollPositionListener.scrollPosition(RecycleScrollListener.SCROLL_BOTTOM);
            }else{
                //滑动到既不是顶部也不是底部的位置
                mOnScrollPositionListener.scrollPosition(RecycleScrollListener.SCROLL_NO_TB);
            }
        }

        //滑动方向监听
        if(mOnScrollDirectionListener!=null){
            if(dy < 0){
                //向上滑动
                mOnScrollDirectionListener.scrollUp(dy);
            }else if(dy > 0){
                //向下滑动
                mOnScrollDirectionListener.scrollDown(dy);
            }
        }
    }

    /**是否在滑动**/
    public interface OnScrollStateListener{
        //true:在滑动  false：未滑动
        void scrollState(boolean isScrolling);
    }

    /**滑动位置监听**/
    public interface OnScrollPositionListener {
        //SCROLL_TOP：滑动到顶部
        //SCROLL_NO_TB：滑动到既不是顶部也不是底部的位置
        //SCROLL_BOTTOM：滑动到底部
        void scrollPosition(int position);
    }

    /**滑动方向监听**/
    public interface OnScrollDirectionListener{
        //向上滑动
        void scrollUp(int dy);
        //向下滑动
        void scrollDown(int dy);
    }

}
