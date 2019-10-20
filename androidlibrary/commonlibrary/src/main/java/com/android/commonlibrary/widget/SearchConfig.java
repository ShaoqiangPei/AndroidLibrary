package com.android.commonlibrary.widget;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;
import com.android.commonlibrary.R;
import java.lang.reflect.Field;

/**
 * Title:搜索框帮助类
 * description:
 * autor:pei
 * created on 2019/10/18
 */
public class SearchConfig {

    private SearchView mSearchView;
    private ImageView mCollapsedIcon;//搜索框中开头的搜索图标
    private SearchView.SearchAutoComplete mSearchAutoComplete;//搜索框中的输入框
    private ImageView mCloseButton;//搜索框中结尾的删除按钮

    public SearchConfig(SearchView searchView){
        this.mSearchView=searchView;

        //搜索框初始化
        init();
        //搜索框默认设置
        defaultConfig();
    }

    /**搜索框初始化**/
    private void init(){
        if(mSearchView==null){
            throw new NullPointerException("======searchView不能为空======");
        }
        mSearchAutoComplete=mSearchView.findViewById(R.id.search_src_text);
        mCloseButton = mSearchView.findViewById(R.id.search_close_btn);
        mCollapsedIcon = mSearchView.findViewById(R.id.search_mag_icon);
    }

    /**搜索框默认设置**/
    private void defaultConfig(){
        mSearchView.setIconifiedByDefault(false);//设置搜索图标是否显示在搜索框内
        //1:回车
        //2:前往
        //3:搜索
        //4:发送
        //5:下一項
        //6:完成
        mSearchView.setImeOptions(3);//设置输入法搜索选项字段，默认是搜索，可以是：下一页、发送、完成等
        //        mSearchView.setInputType(1);//设置输入类型
        //        mSearchView.setMaxWidth(200);//设置最大宽度
        //        mSearchView.setSubmitButtonEnabled(true);//设置是否显示搜索框展开时的提交按钮
        //设置SearchView下划线透明
        setUnderLinetransparent(mSearchView);
    }

    /**设置SearchView下划线透明**/
    private void setUnderLinetransparent(SearchView searchView){
        try {
            Class<?> argClass = searchView.getClass();
            // mSearchPlate是SearchView父布局的名字
            Field ownField = argClass.getDeclaredField("mSearchPlate");
            ownField.setAccessible(true);
            View mView = (View) ownField.get(searchView);
            mView.setBackgroundColor(Color.TRANSPARENT);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**获取搜索框中头部搜索图标对象**/
    public ImageView getCollapsedIcon(){
        return mCollapsedIcon;
    }

    /**获取搜索框中输入框对象**/
    public SearchView.SearchAutoComplete getSearchAutoComplete(){
        return mSearchAutoComplete;
    }

    /**获取搜索框中尾部删除图标对象**/
    public ImageView getCloseButton(){
        return mCloseButton;
    }

    /**获取搜索结果**/
    public String getQueryText(){
        return mSearchAutoComplete.getText().toString();
    }

    /**设置搜索文本监听**/
    public void setOnSearchListener(SearchView.OnQueryTextListener listener){
        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(listener);

        //注：OnQueryTextListener中的两个方法：
        //boolean onQueryTextSubmit(String query) ----- 当点击搜索按钮时触发该方法
        //boolean onQueryTextChange(String newText) ----- 当搜索内容改变时触发该方法
    }

    //========搜索框相关其他方法=========
    //    searchView.setQueryHint("ahdewoi");//设置查询提示字符串
}

//===========使用示例=================
//在布局中加入SearchView控件,注意导包路径为：androidx.appcompat.widget.SearchView
//<androidx.appcompat.widget.SearchView
//        android:id="@+id/view_search"
//        android:layout_width="0dp"
//        android:layout_height="40dp"
//        android:layout_marginLeft="5dp"
//        android:layout_marginRight="10dp"
//        android:layout_marginTop="20dp"
//        android:background="#d6d6d6"
//        app:layout_constraintEnd_toStartOf="@+id/tv_search"
//        app:layout_constraintStart_toStartOf="parent"
//        app:layout_constraintTop_toTopOf="parent" />
//
// 在MainActivity中声明控件和搜索框辅助类
//    private SearchView mSearchView;
//    private SearchConfig mSearchConfig;//搜索框辅助类
//
// 初始化控件
// mSearchView = findViewById(R.id.view_search);
//
// 使用样例：
//    /**初始化搜索**/
//    private void initSearchView(){
//        mSearchConfig=new SearchConfig(mSearchView);
//        //设置搜索框内左侧搜索图标
//        ImageView collapsedIcon=mSearchConfig.getCollapsedIcon();
//        collapsedIcon.setImageResource(R.mipmap.ic_search);
//        //设置搜索框右侧删除图标
//        ImageView closeButton=mSearchConfig.getCloseButton();
//        closeButton.setImageResource(R.mipmap.ic_delete);
//        //搜索框内文字颜色
//        SearchView.SearchAutoComplete searchAutoComplete=mSearchConfig.getSearchAutoComplete();
//        searchAutoComplete.setTextColor(Color.BLACK);
//        //搜索框内文字大小
//        searchAutoComplete.setTextSize(14);
//        //提示语颜色
//        searchAutoComplete.setHintTextColor(ContextCompat.getColor(MainActivity.this,R.color.red));
//        //提示语
//        mSearchView.setQueryHint("请输入搜索内容");//设置查询提示字符串
//
//
//        // 设置搜索文本监听
//        mSearchConfig.setOnSearchListener(new SearchView.OnQueryTextListener() {
//            //当点击搜索按钮时触发该方法
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if(StringUtil.isNotEmpty(query)){
//                    mTv.setText("搜索结果: "+query);
//                }else{
//                    ToastUtil.shortShow("请输入要搜索的内容");
//                }
//                return false;
//            }
//
//            //当搜索内容改变时触发该方法
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//
//        //搜索按钮
//        mTvSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String result=mSearchConfig.getQueryText();
//                if(StringUtil.isNotEmpty(result)){
//                    mTv.setText("搜索结果: "+result);
//                }else{
//                    ToastUtil.shortShow("请输入要搜索的内容");
//                }
//            }
//        });
//    }

