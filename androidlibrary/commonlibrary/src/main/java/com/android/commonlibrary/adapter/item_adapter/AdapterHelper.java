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

    /**设置线性RecyclerView**/
    public void setRecyclerLinearManager(RecyclerView.Adapter adapter, RecyclerView recyclerView, Context context){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    /**设置线性RecycleView间距**/
    public void setLinearLayoutItemSpace(RecyclerView recyclerView, int dp, int colorId, Context context) {
        LinearDividerItemDecoration divider = new LinearDividerItemDecoration(LinearLayoutManager.VERTICAL, ScreenUtil.dp2px(dp, context), ContextCompat.getColor(context, colorId));
        recyclerView.addItemDecoration(divider);
    }

    /**设置九宫格RecyclerView**/
    public void setRecyclerGridManager(RecyclerView.Adapter adapter, RecyclerView recyclerView, int itemCount, Context context){
        GridLayoutManager manager = new GridLayoutManager(context, itemCount);
        manager.setSmoothScrollbarEnabled(true);
        manager.setAutoMeasureEnabled(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    /**设置九宫格间距**/
    public void setGridLayoutItemSpace(RecyclerView recyclerView, int dp, int colorId, Context context) {
        GridDividerItemDecoration gridDividerItemDecoration = new GridDividerItemDecoration(ScreenUtil.dp2px(dp, context), ContextCompat.getColor(context, colorId));
        recyclerView.addItemDecoration(gridDividerItemDecoration);
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

    /**点击事件监听**/
    public interface OnItemClickListener {
        void itemClickListener(View view, BaseViewHolder viewHolder, Object obj);
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

}
