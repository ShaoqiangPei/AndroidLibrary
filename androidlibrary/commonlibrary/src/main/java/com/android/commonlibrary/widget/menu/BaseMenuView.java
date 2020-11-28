package com.android.commonlibrary.widget.menu;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import com.android.commonlibrary.R;
import com.android.commonlibrary.util.ScreenUtil;

/**
 * Title:自定义侧滑菜单控件
 * description:
 * autor:pei
 * created on 2020/11/22
 */
public abstract class BaseMenuView extends ConstraintLayout {

    private static final double SCALE_WIDTH=0.75; //默认菜单宽度与屏幕宽度逼系数

    protected View mLayoutView;
    protected Context mContext;

    //侧滑菜单栏
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;

    public BaseMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mLayoutView = LayoutInflater.from(context).inflate(getLayoutId(), this);
        this.mContext = context;

        //初始化控件
        initView();
        //初始化数据
        initData();
    }

    /**获取布局id**/
    public abstract int getLayoutId();

    /**初始化控件**/
    public abstract void initView();

    /**初始化数据**/
    public abstract void initData();

    /***
     * 初始化
     *
     * @param drawerLayout activiy中 DrawerLayout 根布局对象
     * @param listener
     */
    public void init(DrawerLayout drawerLayout,OnDrawerListener listener){
        if(drawerLayout==null){
            throw new NullPointerException("=====初始化时drawerLayout不能传null========");
        }
        this.mDrawerLayout=drawerLayout;

        //设置默认菜单栏宽度
        setLayoutWidth(SCALE_WIDTH);
        //默认关闭菜单栏手势滑动功能
        setDrawerLockMode(false);

        //创建菜单控制开关
        mActionBarDrawerToggle=new ActionBarDrawerToggle((Activity) mContext,mDrawerLayout,
                R.string.menu_name,R.string.menu_name){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //打开菜单
                listener.opened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //关闭菜单
                listener.closed(drawerView);
            }
        };
        //添加侧滑菜单监听
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
    }

    /**activity的onPostCreate方法中调用,防止手机切屏报错**/
    public void onPostCreate(){
        if(mActionBarDrawerToggle==null){
            throw new NullPointerException("=====请先调用init(DrawerLayout drawerLayout,OnDrawerListener listener)方法========");
        }
        mActionBarDrawerToggle.syncState();
    }

    /**activity的 onConfigurationChanged 方法中调用,防止手机切屏报错**/
    public void onConfigurationChanged(Configuration newConfig){
        if(mActionBarDrawerToggle==null){
            throw new NullPointerException("=====请先调用init(DrawerLayout drawerLayout,OnDrawerListener listener)方法========");
        }
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }


    /***
     *  开启/关闭 菜单栏手势滑动功能
     *
     * @param open：true-开启, false-关闭
     */
    public void setDrawerLockMode(boolean open) {
        if (mDrawerLayout == null) {
            throw new NullPointerException("=====请先调用init(DrawerLayout drawerLayout,OnDrawerListener listener)方法========");
        }
        if (open) {//开启
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,this);
        }else{//关闭
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,this);
        }
    }

    /***
     *  启用/关闭 所有菜单栏手势滑动功能
     *
     *  注:当界面中有多个侧滑菜单栏(即同时有左，右侧滑菜单栏)时,可以用此方法
     *     快速对多个侧滑菜单栏做统一控制
     *
     * @param open:true-开启, false-关闭
     */
    public void setAllDrawerLockMode(boolean open){
        if (mDrawerLayout == null) {
            throw new NullPointerException("=====请先调用init(DrawerLayout drawerLayout,OnDrawerListener listener)方法========");
        }
        if(open){//启动菜单栏手势滑动
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }else {//关闭菜单栏手势滑动
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    /**打开菜单**/
    public void openDrawer(){
        if(mDrawerLayout==null){
            throw new NullPointerException("=====请先调用init(DrawerLayout drawerLayout,OnDrawerListener listener)方法========");
        }
        mDrawerLayout.openDrawer(this);
    }

    /**关闭菜单栏**/
    public void closeDrawer(){
        if(mDrawerLayout==null){
            throw new NullPointerException("=====请先调用init(DrawerLayout drawerLayout,OnDrawerListener listener)方法========");
        }
        mDrawerLayout.closeDrawer(this);
    }

    /***
     * 判断侧滑菜单栏是否已经打开
     *
     * @return true：已经打开
     *         false：已经关闭
     */
    public boolean isDrawerOpen(){
        if(mDrawerLayout==null){
            throw new NullPointerException("=====请先调用init(DrawerLayout drawerLayout,OnDrawerListener listener)方法========");
        }
        return mDrawerLayout.isDrawerOpen(this);
    }

    /***
     * 设置菜单栏宽度
     *
     * @param scaleWidth 0<scaleWidth<1
     */
    public void setLayoutWidth(double scaleWidth){
        if(scaleWidth<=0||scaleWidth>=1){
            throw new SecurityException("====菜单宽度比例系数scaleWidth需要在(0,1)之间===");
        }
        //设置菜单宽度
        int screenWidth= ScreenUtil.getWidth();
        ViewGroup.LayoutParams params=getLayoutParams();
        params.width = (int) (screenWidth*scaleWidth);
        setLayoutParams(params);
    }

    /***
     * 设置为左侧菜单栏
     *
     * 注：也可以在布局中给侧滑菜单控件设置 android:layout_gravity="start" //左菜单栏
     */
    public void setLeftMenu(){
        DrawerLayout.LayoutParams params= (DrawerLayout.LayoutParams) getLayoutParams();
        params.gravity= Gravity.LEFT;
        setLayoutParams(params);
    }

    /***
     * 设置为右侧菜单栏
     *
     * 注：也可以在布局中给侧滑菜单控件设置 android:layout_gravity="end" //右菜单栏
     */
    public void setRightMenu(){
        DrawerLayout.LayoutParams params= (DrawerLayout.LayoutParams) getLayoutParams();
        params.gravity= Gravity.RIGHT;
        setLayoutParams(params);
    }

    /**用于初始化控件的**/
    protected  <T> T getView(int rId) {
        View view = mLayoutView.findViewById(rId);
        return (T) view;
    }

    /**菜单打开/关闭监听**/
    public interface OnDrawerListener{
        void opened(View drawerView);
        void closed(View drawerView);
    }

}
