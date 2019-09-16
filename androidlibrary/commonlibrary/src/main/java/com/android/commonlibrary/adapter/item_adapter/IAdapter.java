package com.android.commonlibrary.adapter.item_adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.animation.BaseAnimation;

/**
 * Description:适配器接口
 *
 * Author:pei
 * Date: 2019/7/8
 */
public interface IAdapter {

    /**item中初始化控件**/
    <T>void initView(BaseViewHolder viewHolder, T obj);

    /**item中初始化数据**/
    <T>void initData(BaseViewHolder viewHolder, T obj);

    /**item中监听事件**/
    <T>void setListener(BaseViewHolder viewHolder, T obj);

    /**获取headerView**/
    View getHeaderView(int headerViewId);

    /**获取footerView**/
    View getFooterView(int footerViewId);

    /**添加headerView**/
    void addHeaderView(int headerViewId);

    /**添加footerView**/
    void addFooterView(int footerViewId);

    /**adapter渐现动画**/
    void openAlphaAnimation();

    /**adapter缩放动画**/
    void openScaleAnimation();

    /**adapter从下到上动画**/
    void openBottomAnimation();

    /**adapter从左到右动画**/
    void openLeftAnimation();

    /**adapter从右到左动画**/
    void openRightAnimation();

    /**自定义动画**/
    void openLoadAnimation(BaseAnimation animation);

    /**设置String值(String)**/
    String getAdapterString(String s);

    /**设置String值(int)**/
    String getAdapterString(int i);

    /**设置线性RecyclerView**/
    void setRecyclerLinearManager(RecyclerView recyclerView);

    /**设置线性RecycleView间距**/
    void setLinearLayoutItemSpace(RecyclerView recyclerView, int dp, int colorId);

    /**设置九宫格RecyclerView**/
    void setRecyclerGridManager(RecyclerView recyclerView, int itemCount);

    /**设置九宫格间距**/
    void setGridLayoutItemSpace(RecyclerView recyclerView, int dp, int colorId);

    /**获取position，当添加有header或footer要注意改变**/
    int getPosition(BaseViewHolder viewHolder);

    /**设置点击事件监听**/
    void setOnItemClickListener(AdapterHelper.OnItemClickListener onItemClickListener);

    /**添加控件监听**/
    void addOnClickListener(View view, final BaseViewHolder viewHolder, final Object obj);
}
