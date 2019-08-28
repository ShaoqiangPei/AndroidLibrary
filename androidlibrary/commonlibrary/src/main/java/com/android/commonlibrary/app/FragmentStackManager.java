package com.android.commonlibrary.app;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.commonlibrary.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Title:Fragment回退栈管理类
 *       含跳转，关闭Fragment等相关方法。
 *
 * Description:
 *
 * Create by pei
 * Date:2019/8/7
 */
public class FragmentStackManager {

    private static final int DEFAULT_INDEX=-1;

    private FragmentStackManager() {
    }

    private static class Holder {
        private static FragmentStackManager instance = new FragmentStackManager();
    }

    public static FragmentStackManager getInstance() {
        return Holder.instance;
    }

    /***
     * 跳转fragmnet并添加到回退栈
     *
     * @param containerId 装fragment的控件id，一般是FrameLayout的id
     * @param context 上下文
     * @param tag fragment对应的tag
     * @param bundle 传值用的bundle
     * @param listener 创建一个frgmment实例的监听
     * @return
     */
    public Fragment startFragment(int containerId, Context context, String tag, Bundle bundle, OnCreateFragmentListener listener){
        if(StringUtil.isEmpty(tag)){
            throw new NullPointerException("====跳转Fragment时,fragment的tag不能为空=======");
        }
        FragmentManager manager= ((FragmentActivity)context).getSupportFragmentManager();
        Fragment fragment=manager.findFragmentByTag(tag);
        if(fragment==null){
            fragment=listener.createFragment();
        }
        if(!fragment.isAdded()) {
            FragmentTransaction transaction=manager.beginTransaction();
            //传值
            if(bundle!=null){
                fragment.setArguments(bundle);
            }
            transaction.add(containerId, fragment, tag);
            //添加到回退栈
            transaction.addToBackStack(tag);
            transaction.commitAllowingStateLoss();
        }
        return fragment;
    }

    /**涉及跳转的接口类**/
    public interface OnCreateFragmentListener{
        Fragment createFragment();
    }

    /***
     * 关闭回退栈中最后一个Fragment
     * 即关闭当前fragment
     */
    public void finish(Context context){
        FragmentManager fm=((FragmentActivity)context).getSupportFragmentManager();
        if(fm!=null){
            fm.popBackStack();
        }
    }

    /***
     * 返回到指定已经打开的fragment,并关闭当前及当前与指定Fragment间的所有Fragment
     * @param tag 需要返回到指定Fragment的tag
     *            若你需要返回到FragmentOne,且FragmentOne的tag为“one”,则此处tag参数填“one”
     * @param context
     */
    public void goBackToFragmentByTag(String tag,Context context){
        if(StringUtil.isEmpty(tag)){
            return;
        }
        List<String>tagList=getFragmentTags(context);
        if(tagList.isEmpty()){
            return;
        }
        int index=getFragmentTagIndex(tag,context);
        if(index==DEFAULT_INDEX){
            //若在回退栈中中不到tag对应下标则退出方法
            return;
        }
        if(index==tagList.size()-1){
            //若tag下标为最后一个,则说明需要关闭当前fragment
            finish(context);
            return;
        }
        //若index不为最后一个，则关闭当前及当前与指定Fragment间的所有Fragment
        String indexTag=tagList.get(index+1);
        if(StringUtil.isNotEmpty(indexTag)) {
            FragmentManager fm=((FragmentActivity)context).getSupportFragmentManager();
            if(fm!=null){
                fm.popBackStackImmediate(indexTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }

    /**关闭回退栈中所有Fragment**/
    public void finishAllFragments(Context context){
        FragmentManager fm=((FragmentActivity)context).getSupportFragmentManager();
        if(fm!=null){
            fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    private int getFragmentTagIndex(String tag,Context context){
        if(StringUtil.isEmpty(tag)){
            return DEFAULT_INDEX;
        }
        List<String>tagList=getFragmentTags(context);
        if(tagList.isEmpty()){
            return DEFAULT_INDEX;
        }
        for (int i = 0; i < tagList.size(); i++) {
            if(tag.equals(tagList.get(i))){
                return i;
            }
        }
        return DEFAULT_INDEX;
    }

    /**获取回退栈中fragment个数**/
    public int getFragmentSize(Context context){
        int size=0;
        FragmentManager fm=((FragmentActivity)context).getSupportFragmentManager();
        if(fm!=null){
            size=fm.getBackStackEntryCount();
        }
        return size;
    }

    /**获取回退栈中fragment对应的tag集合**/
    public List<String> getFragmentTags(Context context){
        List<String>tagList=new ArrayList<>();
        FragmentManager fm=((FragmentActivity)context).getSupportFragmentManager();
        if(fm!=null){
            int size=fm.getBackStackEntryCount();
            if(size>0){
                for (int i = 0; i < size; i++) {
                    FragmentManager.BackStackEntry backStackEntry=fm.getBackStackEntryAt(i);
                    if(backStackEntry!=null){
                        //获取回退栈中Fragment对应的tag
                        String tag=backStackEntry.getName();
                        tagList.add(tag);
                    }
                }
            }
        }
        return tagList;
    }

}
