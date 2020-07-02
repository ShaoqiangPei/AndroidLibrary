package com.android.commonlibrary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.commonlibrary.activity.AppHelper;
import com.android.commonlibrary.activity.SuperActivity;
import com.android.commonlibrary.interfacer.IActivity;
import com.android.commonlibrary.interfacer.OnFragmentListener;
import com.android.commonlibrary.util.DoubleClickUtil;
import java.io.Serializable;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:Fragment超类
 *
 * Author:pei
 * Date: 2019/7/4
 */
public abstract class SuperFragment extends Fragment implements IActivity{

    protected View mLayoutView;//总布局
    protected Context mContext;
    private Unbinder mUnbinder;
    protected boolean isFirstTimeLoad=true;
    protected boolean mFragmentCreated;

    /**与Activity交互回调监听**/
    protected OnFragmentListener mOnFragmentListener;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mContext=context;
    }

    /**设置监听**/
    public void setOnFragmentListener(OnFragmentListener onFragmentListener){
        this.mOnFragmentListener=onFragmentListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewId() != 0) {
            mLayoutView = inflater.inflate(getContentViewId(), container, false);
            mUnbinder = ButterKnife.bind(this, mLayoutView);//绑定framgent
            //加载mvp框架的时候用
            loadMVP();
            //初始化
            onCreateFragmentView(inflater, container, savedInstanceState);
            return mLayoutView;
        }else{
            throw new SecurityException("====请在Fagment的getContentViewId中给activity设置对应的xml文件id=======");
        }
    }

    protected void onCreateFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initData();
        setListener();
        mFragmentCreated=true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()&&mFragmentCreated){//fragment界面可见时调用
            if(isFirstTimeLoad) {//第一次界面可见
                onVisible(true);
                isFirstTimeLoad=false;
            }else{//非第一次界面可见
                onVisible(false);
            }
        }else{//fragment界面不可见时调用
            onInvisible();
        }
    }

    /**加载mvp框架的时候用，供子类重写，此处不做处理**/
    protected void loadMVP(){}
    /**fragment可见,子类需要用到时重写**/
    protected  void onVisible(boolean isFirstTimeLoad){}
    /**fragment不可见,子类需要用到时重写**/
    protected  void onInvisible(){}

    @Override
    public void onClick(View v) {
        DoubleClickUtil.shakeClick(v);
    }

    @Override
    public void onDestroy() {
        if(mUnbinder!=null){
            mUnbinder.unbind();
        }
        super.onDestroy();
    }

    /**获取控件值**/
    @Override
    public String getTextOfView(TextView textView) {
        return ((SuperActivity)mContext).getTextOfView(textView);
    }

    /**获取非空字符串**/
    @Override
    public String getNotEmptyString(String str) {
        return ((SuperActivity)mContext).getNotEmptyString(str);
    }

    /**长吐司**/
    @Override
    public void showToast(String msg) {
        ((SuperActivity)mContext).showToast(msg);
    }

    /**短吐司**/
    @Override
    public void showShortToast(String msg) {
        ((SuperActivity)mContext).showShortToast(msg);
    }

    /**用于初始化控件的**/
    @Override
    public <T> T getView(int rId) {
        return AppHelper.getInstance().getView(mLayoutView,rId);
    }

    /**无参界面跳转**/
    @Override
    public void startAct(Class<?> cls) {
        ((SuperActivity)mContext).startAct(cls);
    }

    /**含一个参数的界面跳转**/
    @Override
    public void startParameterAct(Class<?> cls, String tag, Object parameter) {
        ((SuperActivity)mContext).startParameterAct(cls,tag,parameter);
    }

    /**接收上一个界面传过来的int**/
    @Override
    public int getIntParameter(String tag) {
        return ((SuperActivity)mContext).getIntParameter(tag);
    }

    /**接收上一个界面传过来的String**/
    @Override
    public String getStringParameter(String tag) {
        return ((SuperActivity)mContext).getStringParameter(tag);
    }

    /**接收上一个界面传过来的boolean**/
    @Override
    public boolean getBooleanParameter(String tag) {
        return ((SuperActivity)mContext).getBooleanParameter(tag);
    }

    /**接收上一个界面传过来的Bundle**/
    @Override
    public Bundle getBundleParameter(String tag) {
        return ((SuperActivity)mContext).getBundleParameter(tag);
    }

    /**接收上一个界面传过来的对象，对象需要实现Serializable**/
    @Override
    public Serializable getSerializableObject(String tag) {
        return ((SuperActivity)mContext).getSerializableObject(tag);
    }

    /**接收上一个界面传过来的对象，对象需要实现Parcelable**/
    @Override
    public Parcelable getParcelableObject(String tag) {
        return ((SuperActivity)mContext).getParcelableObject(tag) ;
    }

    /**传一个int集合的界面跳转**/
    @Override
    public void startIntegerListAct(Class<?> cls, String tag, List<Integer> list) {
        ((SuperActivity)mContext).startIntegerListAct(cls,tag,list);
    }

    /**接收上一个界面传过来的int集合**/
    @Override
    public List<Integer> getIntegerList(String tag) {
        return ((SuperActivity)mContext).getIntegerList(tag);
    }

    /**传一个String集合的界面跳转**/
    @Override
    public void startStringListAct(Class<?> cls, String tag, List<String> list) {
        ((SuperActivity)mContext).startStringListAct(cls,tag,list);
    }

    /**接收上一个界面传过来的String集合**/
    @Override
    public List<String> getStringList(String tag) {
        return ((SuperActivity)mContext).getStringList(tag);
    }

    /**传一个object集合的界面跳转,集合中的object需要实现Parcelable接口**/
    @Override
    public void startParcelableListAct(Class<?> cls, String tag, List<? extends Parcelable> list) {
        ((SuperActivity)mContext).startParcelableListAct(cls,tag,list);
    }

    /**接收上一个界面传过来的object集合,集合中的object需要实现Parcelable接口**/
    @Override
    public List<? extends Parcelable> getParcelableList(String tag) {
        return ((SuperActivity)mContext).getParcelableList(tag);
    }

    /**下一个Fragemnt中接收传值**/
    protected Bundle getFragBundle(){
        Bundle bundle=getArguments();
        if(bundle!=null){
            return bundle;
        }
        return null;
    }

}