## RecycleScrollListener使用说明

### 概述
`RecycleScrollListener`作为一个`RecyclerView`的监听类，可以用于快速处理`RecyclerView`是否滑动，滑动位置(顶部/底部),滑动方向(向上/向下)的监听。

### 使用说明
#### 一. 监听是否滑动
监听`RecyclerView`是否滑动可以像下面这样处理：
```
        RecycleScrollListener scrollListener=new RecycleScrollListener();
        //是否滑动监听
        scrollListener.setOnScrollStateListener(new RecycleScrollListener.OnScrollStateListener() {
            @Override
            public void scrollState(boolean isScrolling) {
                if(isScrolling){
                    ToastUtil.shortShow("正在滑动");
                    LogUtil.i("======正在滑动======");
                }else{
                    ToastUtil.shortShow("未滑动");
                    LogUtil.i("======未滑动======");
                }
            }
        });
        mRecyclerView.addOnScrollListener(scrollListener);
```
#### 二.监听是否滑动到列表 顶部/底部
监听`RecyclerView`是否滑动到列表顶部/底部 可以类似下面这样：
```
        RecycleScrollListener scrollListener=new RecycleScrollListener();
        //滑动位置监听
        scrollListener.setOnScrollPositionListener(new RecycleScrollListener.OnScrollPositionListener() {
            @Override
            public void scrollPosition(int position) {
                switch (position) {
                    case RecycleScrollListener.SCROLL_TOP:
                        ToastUtil.shortShow("顶部");
                        LogUtil.i("====顶部====");
                        break;
                    case RecycleScrollListener.SCROLL_BOTTOM:
                        ToastUtil.shortShow("底部");
                        LogUtil.i("====底部====");
                        break;
                    default:
//                        ToastUtil.shortShow("非顶部和底部的位置");
//                        LogUtil.i("====非顶部和底部的位置====");
                        break;
                }
            }
        });
        mRecyclerView.addOnScrollListener(scrollListener);
```
#### 三. 监听是否 向上/向下 滑
监听列表是否 向上/向下 滑，可以像下面这样操作：
```
        RecycleScrollListener scrollListener=new RecycleScrollListener();
        //滑动方向监听
        scrollListener.setOnScrollDirectionListener(new RecycleScrollListener.OnScrollDirectionListener() {

            @Override
            public void scrollUp(int dy) {
                ToastUtil.shortShow("向上滑");
                LogUtil.i("====向上滑====");
            }

            @Override
            public void scrollDown(int dy) {
                ToastUtil.shortShow("向下滑");
                LogUtil.i("====向下滑====");
            }
        });
        mRecyclerView.addOnScrollListener(scrollListener);
```

