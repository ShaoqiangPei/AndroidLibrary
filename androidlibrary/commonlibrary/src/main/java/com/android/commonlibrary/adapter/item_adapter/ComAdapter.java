package com.android.commonlibrary.adapter.item_adapter;

import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

import com.android.commonlibrary.adapter.item_divider.GridDividerItemDecoration;
import com.android.commonlibrary.adapter.item_divider.LinearDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.animation.BaseAnimation;
import java.util.List;

/**
 * Title:RecyclerView通用适配器基类
 * Description:
 *
 * Created by pei
 * Date: 2017/12/29
 */
public abstract class ComAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> implements IAdapter{

    protected Context mContext;
    protected List<T> mData;
    protected int mItemViewId;//item布局id
    protected AdapterHelper.OnItemClickListener mOnItemClickListener;

    public ComAdapter(int itemViewId, List<T> data, Context context) {
        super(itemViewId, data);
        this.mItemViewId = itemViewId;
        this.mContext = context;
        this.mData = data;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, T obj) {
        initView(viewHolder, obj);
        initData(viewHolder, obj);
        setListener(viewHolder, obj);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    /**获取headerView**/
    @Override
    public View getHeaderView(int headerViewId) {
        return AdapterHelper.getInstance().getLayoutView(headerViewId,mContext);
    }

    /**获取footerView**/
    @Override
    public View getFooterView(int footerViewId) {
        return AdapterHelper.getInstance().getLayoutView(footerViewId,mContext);
    }

    /**添加headerView**/
    @Override
    public void addHeaderView(int headerViewId) {
        addHeaderView(getHeaderView(headerViewId));
    }

    /**添加footerView**/
    @Override
    public void addFooterView(int footerViewId) {
        addFooterView(getFooterView(footerViewId));
    }

    /**adapter渐现动画**/
    @Override
    public void openAlphaAnimation() {
        openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }

    /**adapter缩放动画**/
    @Override
    public void openScaleAnimation() {
        openLoadAnimation(BaseQuickAdapter.SCALEIN);
    }

    /**adapter从下到上动画**/
    @Override
    public void openBottomAnimation() {
        openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
    }

    /**
     * adapter从左到右动画
     **/
    @Override
    public void openLeftAnimation() {
        openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
    }

    /**
     * adapter从右到左动画
     **/
    @Override
    public void openRightAnimation() {
        openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
    }

    /**
     * 自定义动画
     **/
    @Override
    public void openLoadAnimation(BaseAnimation animation) {

    }

    /**
     * 设置String值(String)
     **/
    @Override
    public String getAdapterString(String s) {
        return AdapterHelper.getInstance().getAdapterString(s);
    }

    /**
     * 设置String值(int)
     **/
    @Override
    public String getAdapterString(int i) {
        return AdapterHelper.getInstance().getAdapterString(i);
    }

    /**设置线性RecyclerView**/
    @Override
    public void setRecyclerLinearManager(RecyclerView recyclerView) {
        setRecyclerLinearManager(recyclerView,false);
    }

    /**设置线性RecyclerView**/
    public void setRecyclerLinearManager(RecyclerView recyclerView,boolean NestedScrollingEnabled) {
        AdapterHelper.getInstance().setRecyclerLinearManager(this,recyclerView,NestedScrollingEnabled,mContext);
        //默认adapter渐现效果
        openLoadAnimation();
    }

    /**设置线性RecycleView间距**/
    @Override
    public LinearDividerItemDecoration setLinearLayoutItemSpace(RecyclerView recyclerView, int dp, int colorId) {
        return AdapterHelper.getInstance().setLinearLayoutItemSpace(recyclerView,dp,colorId,mContext);
    }

    /**设置九宫格RecyclerView**/
    @Override
    public void setRecyclerGridManager(RecyclerView recyclerView, int itemCount) {
        openLoadAnimation();//默认adapter渐现效果
        AdapterHelper.getInstance().setRecyclerGridManager(this,recyclerView,itemCount,mContext);
    }

    /**设置九宫格间距**/
    @Override
    public GridDividerItemDecoration setGridLayoutItemSpace(RecyclerView recyclerView, int dp, int colorId) {
        return AdapterHelper.getInstance().setGridLayoutItemSpace(recyclerView,dp,colorId,mContext);
    }

    /**移除RecycleView间距**/
    @Override
    public void removeItemSpace(RecyclerView recyclerView, RecyclerView.ItemDecoration divider) {
        AdapterHelper.getInstance().removeItemSpace(recyclerView,divider);
    }

    @Override
    public int getPosition(BaseViewHolder viewHolder) {
        return AdapterHelper.getInstance().getPosition(viewHolder);
    }

    /**设置点击事件监听**/
    @Override
    public void setOnItemClickListener(AdapterHelper.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener=onItemClickListener;
    }

    /**添加控件监听**/
    @Override
    public void addOnClickListener(View view, BaseViewHolder viewHolder, Object obj) {
        AdapterHelper.getInstance().addOnClickListener(mOnItemClickListener,view,viewHolder,obj);
    }

}

//==================使用范例=============
//public class NameAdapter<T>extends ComAdapter {
//
//    private TextView mTvName;
//
//    public NameAdapter(List<T> data, Context context){
//        super(R.layout.item_layout,data,context);
//    }
//
//    @Override
//    protected <T>void initView(BaseViewHolder viewHolder, Object o) {
//        mTvName=viewHolder.getView(R.id.tv_name);
//    }
//
//    @Override
//    protected <T>void initData(BaseViewHolder viewHolder, Object o) {
//        mTvName.setText(o.toString());
//    }
//
//    @Override
//    protected <T>void setListener(BaseViewHolder viewHolder, Object o) {
//        addOnClickListener(mTvName,viewHolder,obj);
//    }
// }
//
//
//==========Activity中调用示例================
//
//        mNameList = new ArrayList<>();
//        mNameAdapter = new NameAdapter(mNameList, mContext);
//        //设置线性布局
//        mHeadsetAdapter.setRecyclerLinearManager(mRecyclerView);
//        mNameAdapter.setLinearLayoutItemSpace(mRecyclerView,5,R.color.colorAccent);//设置分割线
//
//        //设置九宫格布局
//        mNameAdapter.setRecyclerGridManager(mRecyclerView,4);
//        mNameAdapter.setGridLayoutItemSpace(mRecyclerView,5,R.color.colorAccent);
//
//        //点击事件
//        mNameAdapter.setOnItemClickListener(new AdapterHelper.OnItemClickListener() {
//           @Override
//           public void itemClickListener(View view, BaseViewHolder viewHolder, Object obj) {
//               switch (view.getId()) {
//                 case R.id.tv:
//                 ToastUtil.shortShow("====1====="+obj.toString());
//                 break;
//               default:
//                  break;
//               }
//            }
//          });






