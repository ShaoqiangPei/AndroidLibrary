package com.android.commonlibrary.ui.manager.frag;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.android.commonlibrary.util.CollectionUtil;
import com.android.commonlibrary.util.StringUtil;
import java.util.Iterator;
import java.util.Stack;

/**
 * Description:Fragment跳转,移除管理类(非回退站管理方式)
 *
 * Author:pei
 * Date: 2019/7/15
 */
public class AppFragmentManager {

    private Stack<String>mFragmentStack;

    private AppFragmentManager(){}

    private static class Holder {
        private static AppFragmentManager instance = new AppFragmentManager();
    }

    public static AppFragmentManager getInstance() {
        return Holder.instance;
    }

    private void addFragmentTag(String tag){
        if(StringUtil.isEmpty(tag)){
            return;
        }
        if(mFragmentStack==null){
            mFragmentStack=new Stack<>();
        }
        if(!mFragmentStack.contains(tag)){
            mFragmentStack.push(tag);
        }
    }

    /**获取存在的Fragment数量**/
    public int getFragmentSize(){
        if(CollectionUtil.isNotEmpty(mFragmentStack)){
            return mFragmentStack.size();
        }
        return 0;
    }

    /***
     *  打开一个Fragment,其他Fragment隐藏
     *
     * @param containerId 加载fragment的容器id
     * @param tag fragment对应的tag
     * @param bundle 传值
     * @param listener 若fragment为空则在这个listener中创建fragment
     * @return
     */
    public Fragment startFragmentOtherHide(int containerId, Context context, String tag, Bundle bundle, OnCreateFragmentListener listener) {
        if(StringUtil.isEmpty(tag)){
            throw new NullPointerException("====跳转Fragment时,fragment的tag不能为空=======");
        }
        FragmentManager manager = ((FragmentActivity)context).getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(tag);
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment == null) {
            fragment = listener.createFragment();
            transaction.add(containerId, fragment, tag);
            //添加到stack
            addFragmentTag(tag);
        } else {
            transaction.show(fragment);
        }
        //隐藏其他fragment
        if(CollectionUtil.isNotEmpty(mFragmentStack)) {
            for (String fragmentTag : mFragmentStack) {
                if (!fragmentTag.equals(tag)) {
                    Fragment otherfragment = manager.findFragmentByTag(fragmentTag);
                    if (otherfragment != null) {
                        transaction.hide(otherfragment);
                    }
                }
            }
        }
        //传值
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        transaction.commitAllowingStateLoss();
        return fragment;
    }


    /***
     *  单纯的打开一个Fragment
     *
     * @param containerId 加载fragment的容器id
     * @param tag fragment对应的tag
     * @param bundle 传值
     * @param listener 若fragment为空则在这个listener中创建fragment
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
            //添加到stack
            addFragmentTag(tag);
            transaction.commitAllowingStateLoss();
        }
        return fragment;
    }

    /**根据tag关闭对应Fragment**/
    public void finishFragmentByTag(String tag,Context context){
        if(StringUtil.isEmpty(tag)){
            return;
        }
        FragmentManager manager=((FragmentActivity)context).getSupportFragmentManager();
        Fragment fragment=manager.findFragmentByTag(tag);
        if(fragment==null){
            return;
        }
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(fragment);
        if(mFragmentStack!=null&&mFragmentStack.search(tag)!=-1){
            mFragmentStack.remove(tag);
        }
        transaction.commitAllowingStateLoss();
    }

    /**关闭所有fragment**/
    public void finishAllFrdagments(Context context){
        if(CollectionUtil.isNotEmpty(mFragmentStack)){
            Iterator<String> it = mFragmentStack.iterator();
            while (it.hasNext()) {
                String tag = it.next();
                FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment = manager.findFragmentByTag(tag);
                if (fragment != null) {
                    transaction.remove(fragment);
                    transaction.commitAllowingStateLoss();
                }
                it.remove();
            }
        }
    }

    /**涉及跳转的接口类**/
    public interface OnCreateFragmentListener{
        Fragment createFragment();
    }
}
