package com.android.commonlibrary.adapter.item_adapter;

import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.animation.BaseAnimation;
import java.util.List;

/**
 * Description: Recycler分组适配器基类
 *
 * Author:pei
 * Date: 2019/7/6
 */
public abstract class GroupAdapter<T>extends BaseSectionQuickAdapter implements IAdapter{

    protected Context mContext;
    protected List<T> mData;
    protected int mSectionHeadViewId;//分组布局的headerid
    protected int mItemViewId;//item布局id
    protected AdapterHelper.OnItemClickListener mOnItemClickListener;

    public GroupAdapter(int itemViewId, int sectionHeadViewId, List<T> data, Context context) {
        super(itemViewId, sectionHeadViewId, data);
        this.mItemViewId = itemViewId;
        this.mSectionHeadViewId=sectionHeadViewId;
        this.mContext = context;
        this.mData = data;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Object item) {
        initView(viewHolder,item);
        initData(viewHolder,item);
        setListener(viewHolder,item);
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
        AdapterHelper.getInstance().setRecyclerLinearManager(this,recyclerView,mContext);
        //默认adapter渐现效果
        openLoadAnimation();
    }

    /**设置线性RecycleView间距**/
    @Override
    public void setLinearLayoutItemSpace(RecyclerView recyclerView, int dp, int colorId) {
        AdapterHelper.getInstance().setLinearLayoutItemSpace(recyclerView,dp,colorId,mContext);
    }

    @Override
    public void setRecyclerGridManager(RecyclerView recyclerView, int itemCount) {
        openLoadAnimation();//默认adapter渐现效果
        AdapterHelper.getInstance().setRecyclerGridManager(this,recyclerView,itemCount,mContext);
    }

    @Override
    public void setGridLayoutItemSpace(RecyclerView recyclerView, int dp, int colorId) {
        //分组九宫格的分割线需要在adapter中具体用代码实现，或者在布局中布局实现，不可调用此方法
        String errorMessage="分组九宫格的分割线需要在adapter中具体用代码实现，或者在布局中布局实现,不可调用此方法";
        throw new SecurityException(errorMessage);
    }

    @Override
    public int getPosition(BaseViewHolder viewHolder) {
        return AdapterHelper.getInstance().getPosition(viewHolder);
    }

    @Override
    public void setOnItemClickListener(AdapterHelper.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener=onItemClickListener;
    }

    @Override
    public void addOnClickListener(View view, BaseViewHolder viewHolder, Object obj) {
        AdapterHelper.getInstance().addOnClickListener(mOnItemClickListener,view,viewHolder,obj);
    }

}

//=================使用范例================================
//public class PersonAdapter<T> extends GroupAdapter {
//
//    //header
//    private TextView mTvHeader;
//
//    //item
//    private TextView mTvItem;
//
//
//    public PersonAdapter(List<T> data, Context context) {
//        super(R.layout.item_layout, R.layout.header_layout, data,context);
//    }
//
//    @Override
//    protected void convertHead(BaseViewHolder viewHolder, SectionEntity item) {
//        //标题相关所有都在此处理
//        //标题初始化
//        mTvHeader=viewHolder.getView(R.id.tv_title);
//        //标题数据处理
//        mTvHeader.setText(item.header);
//        //标题点击事件
//        addOnClickListener(mTvHeader,viewHolder,item);
//    }
//
//    @Override
//    public <T>void initView(BaseViewHolder viewHolder, T obj) {
//        mTvItem=viewHolder.getView(R.id.tv);
//
//    }
//
//    @Override
//    public <T>void initData(BaseViewHolder viewHolder, T obj) {
//        Person person= (Person) obj;
//        mTvItem.setText("姓名:"+person.getName()+"     年龄:"+person.getAge());
//    }
//
//    @Override
//    public <T>void setListener(BaseViewHolder viewHolder, T obj) {
//        addOnClickListener(mTvItem,viewHolder,obj);
//    }
//}
//
//==============javaBean对象处理===================
//    javaBean对象Person需要继承SectionEntity，且要重写构造方法Person(boolean isHeader, String header)，如：
//    public class Person extends SectionEntity {
//
//      private String name;
//      private int age;
//
//      public Person(boolean isHeader, String header) {
//          super(isHeader,header);
//      }
//
//      //其他代码省略
//      //......
//    }
//
//==============在MainActivity中调用===================
//
//        mPersonList=new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//        Person person;
//        if(i%7==0){//header
//        person=new Person(true,"标题"+i);
//        }else{//item
//        person=new Person(false,null);
//        person.setName("小黑"+i);
//        person.setAge(25+i);
//        }
//        mPersonList.add(person);
//        }
//        mPersonAdapter=new PersonAdapter<>(mPersonList,MainActivity.this);
//        //        //线性布局
//        //        mPersonAdapter.setRecyclerLinearManager(mRecyclerView);
//        //        mPersonAdapter.setLinearLayoutItemSpace(mRecyclerView, 5, R.color.colorAccent);
//
//        //九宫格布局<分割线不能调用setGridLayoutItemSpace实现，若需要设置的话，只能在布局或adapter中代码动态设置>
//        mPersonAdapter.setRecyclerGridManager(mRecyclerView,3);
//
//        //点击事件
//        mPersonAdapter.setOnItemClickListener(new AdapterHelper.OnItemClickListener() {
//        @Override
//        public void itemClickListener(View view, BaseViewHolder viewHolder, Object obj) {
//        Person person= (Person) obj;
//        switch (view.getId()) {
//        case R.id.tv_title://header
//        ToastUtil.shortShow("===="+person.header+"===");
//        break;
//        case R.id.tv://item
//        ToastUtil.shortShow("===="+person.getName()+"===");
//        break;
//          default:
//        break;
//          }
//        }
//      });

