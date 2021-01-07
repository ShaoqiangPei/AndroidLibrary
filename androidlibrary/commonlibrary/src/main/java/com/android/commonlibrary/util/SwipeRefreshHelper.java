package com.android.commonlibrary.util;

import androidx.annotation.ColorRes;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Title: SwipeRefreshLayout下拉刷新帮助类
 * description:
 * autor:pei
 * created on 2021/1/7
 */
public class SwipeRefreshHelper {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener;

    public SwipeRefreshHelper(SwipeRefreshLayout swipeRefreshLayout) {
        this.mSwipeRefreshLayout = swipeRefreshLayout;
    }

    /***
     * 设置下拉图标主题色
     *
     * @param colorResIds 颜色样式为：R.color.green
     *
     * 最少设置一种颜色
     * 最多可设置四个参数，如：
     *     setColorSchemeResources(R.color.green,R.color.red,R.color.black,R.color.blue)
     */
    public void setColorSchemeResources(@ColorRes int... colorResIds) {
        mSwipeRefreshLayout.setColorSchemeResources(colorResIds);
    }

    /***
     * 设置下拉图标背景色(不设置的话,默认背景色为白色)
     *
     * @param color 颜色样式为：R.color.green
     */
    public void setProgressBackgroundColorSchemeResource(int color) {
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(color);
    }

    /***
     * 初始化下拉刷新
     *
     * 一般在 initData() 中初始化时调用
     */
    public void initRefresh(OnRefreshListener listener){
        //下拉刷新
        mRefreshListener=new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //开始刷新
                mSwipeRefreshLayout.setRefreshing(true);
                //刷新动作处理
                if(listener!=null){
                    listener.refresh();
                }
            }
        };
        mSwipeRefreshLayout.setOnRefreshListener(mRefreshListener);
    }

    /**自动刷新**/
    public void autoRefresh(){
        if(mRefreshListener==null){
            throw new NullPointerException("======请先调用initRefresh()方法=====");
        }
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        mRefreshListener.onRefresh();
    }

    /**停止刷新**/
    public void stopRefresh(){
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                //结束刷新
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**是否正在刷新**/
    public boolean isRefreshing(){
        return mSwipeRefreshLayout.isRefreshing();
    }

    /**刷新监听**/
    public interface OnRefreshListener{
        void refresh();
    }

}
