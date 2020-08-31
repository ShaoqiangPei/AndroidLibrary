package com.android.commonlibrary.util;

import android.content.Context;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import com.android.commonlibrary.adapter.viewpager_adapter.ViewPagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Title:ViewPager与Fragment联合使用时的帮助类
 * description:
 * autor:pei
 * created on 2020/6/4
 */
public class ViewPagerHelper {

    private List<String>mTitleList;
    private List<Fragment> mFragmentList;
    private ViewPagerAdapter mViewPagerAdapter;
    private int mLoadCount = 0;//预加载Fragment个数
    private int mLoadIndex = 0;//初始化时默认显示页码的下标
    private boolean mRemoveBoundShadow=false;//viewPager滑到边界时是否去掉边界阴影,默认滑至边界有阴影

    /**添加标题**/
    public ViewPagerHelper addTitleList(List<String>titleList){
        this.mTitleList=titleList;
        return ViewPagerHelper.this;
    }

    /**
     * 添加fragment
     **/
    public ViewPagerHelper addFragment(Fragment fragment) {
        if (mFragmentList == null) {
            mFragmentList = new ArrayList<>();
        }
        if (fragment != null) {
            mFragmentList.add(fragment);
        }
        return ViewPagerHelper.this;
    }

    /***
     * 设置ViewPger滑至边界是否去掉边界阴影
     *
     * @param removeBoundShadow true:去掉边界阴影  false:滑到边界有阴影
     *                          默认为false,即滑至边界有边界阴影
     */
    public ViewPagerHelper setRemoveBoundShadow(boolean removeBoundShadow) {
        this.mRemoveBoundShadow = removeBoundShadow;
        return ViewPagerHelper.this;
    }

    /**
     * 设置fragment预加载个数(若不设置，则默认预加载mFragmentList集合长度)
     *
     * @param count
     */
    public ViewPagerHelper setLoadCount(int count) {
        if (count < 0) {
            throw new SecurityException("====设置fragment预加载个数count不能小于0======");
        }
        this.mLoadCount = count;
        return ViewPagerHelper.this;
    }

    /**
     * viewpager默认显示page下标(若不设置，默认index=0,即默认ViewPager加载第一页)
     *
     * @param index
     */
    public ViewPagerHelper setLoadIndex(int index) {
        if (index < 0) {
            throw new SecurityException("====设置viewpager默认显示page下标不能小于0======");
        }
        this.mLoadIndex = index;
        return ViewPagerHelper.this;
    }

    /**
     * 初始化
     * @param viewPager
     * @param context
     */
    public void init(ViewPager viewPager, Context context) {
        if (mFragmentList == null) {
            throw new NullPointerException("====mFragmentList为null,初始化之前请先调用'addFragment(Fragment fragment)'方法=====");
        }
        if (viewPager == null) {
            throw new NullPointerException("====viewPager不能为null======");
        }
        mViewPagerAdapter = new ViewPagerAdapter(((FragmentActivity) context).getSupportFragmentManager(), mTitleList,mFragmentList);
        //设置ViewPger滑至边界是否去掉边界阴影
        //true:去掉边界阴影  false:滑到边界有阴影。默认为false,即滑至边界有边界阴影
        if(mRemoveBoundShadow){
            //设置viewPager滑至边界去掉边界阴影
            viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        //设置预加载Fragment个数(若为设置预加载个数则默认加载mFragmentList长度)
        if (mLoadCount == 0) {
            mLoadCount = mFragmentList.size();
        }
        viewPager.setOffscreenPageLimit(mLoadCount);
        //设置ViewPager适配器
        viewPager.setAdapter(mViewPagerAdapter);
        //设置初始化时当前显示标签页下标(默认加载第一页)
        viewPager.setCurrentItem(mLoadIndex);
    }

    /**
     * 获取FragmentList对象
     **/
    public List<Fragment> getFragmentList() {
        if (mFragmentList == null) {
            throw new NullPointerException("====mFragmentList为null,初始化之前请先调用'addFragment(Fragment fragment)'方法=====");
        }
        return mFragmentList;
    }

    /**
     * 获取ViewPager适配器
     **/
    public ViewPagerAdapter getViewPagerAdapter() {
        if (mViewPagerAdapter == null) {
            throw new NullPointerException("====mViewPagerAdapter为null,初始化之前请先调用'init(ViewPager viewPager,Context context)'方法=====");
        }
        return mViewPagerAdapter;
    }

}


//=================ViewPage页面滑动监听示例代码====================
//
//        //viewPager页面滑动监听
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//        @Override
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//        }
//
//        @Override
//        public void onPageSelected(int position) {
//            switch (position) {
//                case 0:
//                    mViewPager.setCurrentItem(position);
//                    ToastUtil.shortShow("第一页");
//                    break;
//                case 1:
//                    mViewPager.setCurrentItem(position);
//                    ToastUtil.shortShow("第二页");
//                    break;
//                case 2:
//                    mViewPager.setCurrentItem(position);
//                    ToastUtil.shortShow("第三页");
//                    break;
//                default:
//                    break;
//            }
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int state) {
//            //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
//        }
//    });




