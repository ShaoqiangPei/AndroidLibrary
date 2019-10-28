## SearchConfig使用说明
### 概述
SearchConfig是一个辅助类，准确的说是 SearchView 控件的一个辅助类，它的出现是为了更加方便快捷的使用 SearchView 控件。当大家开发中有用到Search View时，
结合 SearchConfig 类，可以加快你的开发进程。

### 使用说明
#### 一.注意事项
这里说到的SearchView控件，是AndroidX 版本下的SearchView，即SearchView 的导报路径为：androidx.appcompat.widget.SearchView

#### 二.具体使用
SearchConfig是androidx.appcompat.widget.SearchView的一个辅助类，那当然是要结合SearchView使用的，所以当你有使用到SearchView的时候，
你需要在布局中这样加入SearchView(注意导包路径):
```
    <androidx.appcompat.widget.SearchView
        android:id="@+id/view_search"
        android:layout_width="0dp"
        android:layout_height="40dp"
        android:layout_marginLeft="5dp"
        android:layout_marginRight="10dp"
        android:layout_marginTop="20dp"
        android:background="#d6d6d6"
        app:layout_constraintEnd_toStartOf="@+id/tv_search"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```
在MainActivity中，需要声明SearchView控件和SearchConfig对象：
```
    private SearchView mSearchView;
    private SearchConfig mSearchConfig;//搜索框辅助类
```
接着你需要初始化mSearchView控件：
```
   mSearchView = findViewById(R.id.view_search);
```
当然初始化的时候，还涉及到SearhView的相关配置，这时，你需要SearchConfig的帮助，所以你可以在初始化SearchView后，接着调用以下方法：
```
    /**初始化搜索**/
    private void initSearchView(){
        mSearchConfig=new SearchConfig(mSearchView);
        //设置搜索框内左侧搜索图标
        ImageView collapsedIcon=mSearchConfig.getCollapsedIcon();
        collapsedIcon.setImageResource(R.mipmap.ic_search);
        //设置搜索框右侧删除图标
        ImageView closeButton=mSearchConfig.getCloseButton();
        closeButton.setImageResource(R.mipmap.ic_delete);
        //搜索框内文字颜色
        SearchView.SearchAutoComplete searchAutoComplete=mSearchConfig.getSearchAutoComplete();
        searchAutoComplete.setTextColor(Color.BLACK);
        //搜索框内文字大小
        searchAutoComplete.setTextSize(14);
        //提示语颜色
        searchAutoComplete.setHintTextColor(ContextCompat.getColor(MainActivity.this,R.color.red));
        //提示语
        mSearchView.setQueryHint("请输入搜索内容");//设置查询提示字符串


        // 设置搜索文本监听
        mSearchConfig.setOnSearchListener(new SearchView.OnQueryTextListener() {
            //当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(StringUtil.isNotEmpty(query)){
                    mTv.setText("搜索结果: "+query);
                }else{
                    ToastUtil.shortShow("请输入要搜索的内容");
                }
                return false;
            }

            //当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
```
若你想在界面中，自己布局一个控件btn，使点击的时候触发搜索功能，那么你可以给btn设置以下监听以达到目的：
```
        //搜索按钮
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result=mSearchConfig.getQueryText();
                if(StringUtil.isNotEmpty(result)){
                    mTv.setText("搜索结果: "+result);
                    //以下省略搜索逻辑
                    //......
                }else{
                    ToastUtil.shortShow("请输入要搜索的内容");
                }
            }
        });
```
退出含有SearchView界面时，需要关闭软键盘，你可以在界面销毁方法中像下面这样关闭软键盘：
```
       //在activity退出含SearchView界面的时候,关闭软键盘
       @Override
       public void onDestroyView() {
           super.onDestroyView();
           //关闭键盘
           mSearchConfig.closeKeybord(mContext);
       }
```
