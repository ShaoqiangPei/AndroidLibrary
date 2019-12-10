## Badgetor使用说明

### 概述
Badgetor是一个方便在控件上面显示小圆点消息的工具类,为单例模式。其具备显示数字小圆点和文字小圆点的功能。
其中 显示数字小圆点 的时候，分为精确显示和非精确显示模式。
Badgetor使用简洁方便。

### 使用说明
Badgetor设置参数灵活多变，导致各种 set() get()方法很多，这里我就讲解几个重要那个的方法，其他方法设置或获取大家可以查看源码
#### 1. 设置显示数字小圆点
```
        //设置数字小圆点
        Badgetor.getInstance()
//                .setTextSize(8)//默认文字大小8sp
//                .setTextColor(Color.parseColor("#ffffff"))//默认文字颜色(白色)
//                .setGravity(Gravity.END | Gravity.TOP)//设置Badge显示在view的右上角
//                .setOffset(2)//默认外边距为2dp
//                .setPadding(2)//默认设置内边距为2dp
//                .setBackgroundColor(Color.parseColor("#ff0000"))//设置背景色,默认红色
//                .setBackgroundDrawable(null)//设置背景图片，默认为null
//                .setShadow(false)//设置阴影，默认为false,即不显示
//                .setStoke(false, 0, 0)//设置是否描边属性,默认为false，即不描边
//                .setAnimate(false)//设置是否开启隐藏动画,默认为false,即不开启
                .setExactMode(false)//数字是否显示精确模式，默认false，即不显示
                .showNumPoint(mTv, 450, MainActivity.this);//显示数字小圆点
``` 
- 其中需要注意的是 setExactMode(false) 方法，默认设置是false，表示显示的数目为非精确，即当数目大于99时，小圆点上会显示"99+"
当 setExactMode(true) 方法，设置为 true 时，表示显示数目为精确模式，即小圆点会显示实际数字.
- showNumPoint方法中的count参数大于等于0时才显示，否则不显示数字小圆点。
#### 2. 获取小圆点上的数字[默认获取数目为0]
```
        //获取数目
        int count=Badgetor.getInstance().getPointNum();
```
#### 3. 设置显示文字小圆点
```
        //设置文字小圆点
        Badgetor.getInstance()
//                .setTextSize(8)//默认文字大小8sp
//                .setTextColor(Color.parseColor("#ffffff"))//默认文字颜色(白色)
//                .setGravity(Gravity.END | Gravity.TOP)//设置Badge显示在view的右上角
//                .setOffset(2)//默认外边距为2dp
//                .setPadding(2)//默认设置内边距为2dp
//                .setBackgroundColor(Color.parseColor("#ff0000"))//设置背景色,默认红色
//                .setBackgroundDrawable(null)//设置背景图片，默认为null
//                .setShadow(false)//设置阴影，默认为false,即不显示
//                .setStoke(false, 0, 0)//设置是否描边属性,默认为false，即不描边
//                .setAnimate(false)//设置是否开启隐藏动画,默认为false,即不开启
                .showTextPoint(mTv, "好", MainActivity.this);//显示文字小圆点
```
- 在设置文字小圆点的时候，不需要设置 setExactMode 方法，因为此方法只对数字小圆点有效
- showTextPoint中的参数 text 必须设置为非空字符串才行，否则不会显示文字小圆点
#### 4. 获取文字小圆点上的文字[默认获取值为 null]
```
        //显示文字
        String text=Badgetor.getInstance().getPointText();
```
#### 5. 隐藏小圆点
```
        //隐藏小圆点
        Badgetor.getInstance().hideBadgeView();
```
#### 6. 拖拽结束监听
若你要对 未读消息小圆点 加上拖拽功能，并监听拖拽结束的动作处理，你可以像下面这样：
```
        //设置拖拽模式
        Badgetor.getInstance().setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
            @Override
            public void onDragStateChanged(int dragState, Badge badge, View targetView) {
               //处理拖拽结束后你的逻辑
               //...... 
            }
        });
```

