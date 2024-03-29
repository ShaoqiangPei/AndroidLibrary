package com.android.commonlibrary.adapter.item_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import com.android.commonlibrary.adapter.item_divider.GridDividerItemDecoration;
import com.android.commonlibrary.adapter.item_divider.LinearDividerItemDecoration;
import com.android.commonlibrary.util.ScreenUtil;
import com.android.commonlibrary.util.StringUtil;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Description:适配器帮助类
 *
 * Author:pei
 * Date: 2019/7/8
 */
public class AdapterHelper {

    private AdapterHelper(){}

    private static class Holder {
        private static AdapterHelper instance = new AdapterHelper();
    }

    public static AdapterHelper getInstance() {
        return Holder.instance;
    }

    /**根据布局id获取布局对象**/
    public View getLayoutView(int layoutId, Context context){
        if (context != null&&layoutId!=0) {
            return LayoutInflater.from(context).inflate(layoutId, null);
        }
        return null;
    }

    /**设置String值**/
    public String getAdapterString(Object obj){
        String vaule="";
        if(obj==null) {
            return vaule;
        }else if(obj instanceof Integer){
            vaule = String.valueOf((int)obj);
        }else if(obj instanceof String){
            vaule = StringUtil.isNotEmpty(obj.toString()) ? obj.toString() : vaule;
        }
        return vaule;
    }

    /***
     * 设置线性RecyclerView
     *
     * NestedScrollingEnabled：参数默认为 false 的设置
     */
    public void setRecyclerLinearManager(RecyclerView.Adapter adapter, RecyclerView recyclerView, Context context){
        setRecyclerLinearManager(adapter,recyclerView,false,context);
    }

    /***
     * 设置线性RecyclerView
     *
     * @param NestedScrollingEnabled：可活动设置recyclerView.setNestedScrollingEnabled(boolean scroll)参数
     */
    public void setRecyclerLinearManager(RecyclerView.Adapter adapter, RecyclerView recyclerView, boolean NestedScrollingEnabled,Context context){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setNestedScrollingEnabled(NestedScrollingEnabled);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    /**设置线性RecycleView间距**/
    public LinearDividerItemDecoration setLinearLayoutItemSpace(RecyclerView recyclerView, int dp, int colorId, Context context) {
        LinearDividerItemDecoration divider = new LinearDividerItemDecoration(LinearLayoutManager.VERTICAL, ScreenUtil.dp2px(dp, context), ContextCompat.getColor(context, colorId));
        recyclerView.addItemDecoration(divider);
        return divider;
    }

    /***
     * 设置九宫格RecyclerView
     *
     * NestedScrollingEnabled：参数默认为 false 的设置
     */
    public void setRecyclerGridManager(RecyclerView.Adapter adapter, RecyclerView recyclerView, int itemCount, Context context){
        setRecyclerGridManager(adapter,recyclerView,false,itemCount,context);
    }

    /***
     * 设置九宫格RecyclerView
     *
     * @param NestedScrollingEnabled：可活动设置recyclerView.setNestedScrollingEnabled(boolean scroll)参数
     */
    public void setRecyclerGridManager(RecyclerView.Adapter adapter, RecyclerView recyclerView, boolean NestedScrollingEnabled,int itemCount, Context context){
        GridLayoutManager manager = new GridLayoutManager(context, itemCount);
        manager.setSmoothScrollbarEnabled(true);
        manager.setAutoMeasureEnabled(true);
        recyclerView.setNestedScrollingEnabled(NestedScrollingEnabled);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    /**设置九宫格间距**/
    public GridDividerItemDecoration setGridLayoutItemSpace(RecyclerView recyclerView, int dp, int colorId, Context context) {
        GridDividerItemDecoration divider = new GridDividerItemDecoration(ScreenUtil.dp2px(dp, context), ContextCompat.getColor(context, colorId));
        recyclerView.addItemDecoration(divider);
        return divider;
    }

    /**移除RecycleView间距**/
    public void removeItemSpace(RecyclerView recyclerView,  RecyclerView.ItemDecoration divider) {
        if (divider != null) {
            recyclerView.removeItemDecoration(divider);
        }
    }

    /**获取position，当添加有header或footer要注意改变**/
    public int getPosition(BaseViewHolder viewHolder) {
        return viewHolder.getLayoutPosition();
    }

    /**
     * 添加控件监听
     **/
    public void addOnClickListener(OnItemClickListener listener, View view, final BaseViewHolder viewHolder, final Object obj) {
        //mTvName点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.itemClickListener(v, viewHolder, obj);
                }
            }
        });
    }

    /**
     * 添加控件长按监听
     **/
    public void addOnLongClickListener(OnLongItemClickListener listener, View view, final BaseViewHolder viewHolder, final Object obj) {
        //mTvName点击事件
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    listener.longItemClickListener(v, viewHolder, obj);
                }
                return false;
            }
        });
    }

    /**点击事件监听**/
    public interface OnItemClickListener {
        void itemClickListener(View view, BaseViewHolder viewHolder, Object obj);
    }

    /**长按事件监听**/
    public interface OnLongItemClickListener {
        void longItemClickListener(View view, BaseViewHolder viewHolder, Object obj);
    }

    /***
     * 滑动到指定位置(此position会列表置顶)
     * @param context
     * @param position
     */
    public void moveToPosition(RecyclerView recyclerView, int position, Context context){
        LinearSmoothScroller scroller= new LinearSmoothScroller(context){
            @Override
            protected int getHorizontalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }

            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        try {
            LinearLayoutManager manager= (LinearLayoutManager) recyclerView.getLayoutManager();
            scroller.setTargetPosition(position);
            manager.startSmoothScroll(scroller);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

//    //在九宫格adapter初始化方法中设置item高度
//    //此段代码仅作使用参考
//    public void itemHeight() {
//        //九宫格设置item高度[思路:(屏幕宽度-间隙*间隙个数)/item个数]
//        int height = (ScreenUtil.getWidth() - ScreenUtil.dp2px(5, context)*2) / 3;
//        ViewUtil.setViewHeight(height, view);
//    }

}
