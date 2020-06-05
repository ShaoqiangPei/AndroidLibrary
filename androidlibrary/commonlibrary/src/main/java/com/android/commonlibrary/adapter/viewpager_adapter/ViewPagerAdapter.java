package com.android.commonlibrary.adapter.viewpager_adapter;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Title:ViewPager与Fragment联合使用时的适配器
 * description:
 * autor:pei
 * created on 2020/6/4
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> mFragmentList) {
        super(fm);
        this.mFragmentList = mFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        // TODO Auto-generated method stub
        return mFragmentList == null ? null : mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        //       super.destroyItem(container, position, object);
    }
}
