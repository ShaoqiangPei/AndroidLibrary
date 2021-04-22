## GestureHelper使用说明

### 概述
`GestureHelper`主要用来快速实现无障碍服务`AccessibilityService`中的手势相关操作，如`水平滑动`,`竖直滑动`,`手势点击`等。

### 使用说明
#### 一.GestureHelper主要方法
`GestureHelper`主要有以下方法：
```
    /***
     * 设置屏幕尺寸
     *
     * @param currentScreenWidth 开发设备的实际屏幕宽度值
     * @param currentScreenHeight 开发设备的实际屏幕高度值
     * @return
     */
    public GestureHelper initSize(int currentScreenWidth,int currentScreenHeight)
    
    /**手指竖直方向滑动**/
    public boolean verticalSlide(AccessibilityService service,
                                 int startY, int endY,
                                 long startTime,long duration)
                                 
    /**手指竖直方向滑动**/
    public boolean verticalSlide(AccessibilityService service,
                                 int startY, int endY) 
                                 
    /**手指水平方向滑动**/
    public boolean horizontalSlide(AccessibilityService service,
                                   int startX,int endX,
                                   long startTime,long duration)
                                   
    /**手指水平方向滑动**/
    public boolean horizontalSlide(AccessibilityService service,
                        int startX,int endX)
                        
    /**手势点击**/
    public boolean clickByGesture(AccessibilityService service, int x, int y,
                                  long startTime,long duration)
                                  
    /**手势点击**/
    public boolean clickByGesture(AccessibilityService service, int x, int y)
```
#### 二.GestureHelper使用示例
初始化(假设手机屏幕宽高为 1081x1792):
```
        GestureHelper.getInstance().initSize(1080,1792);
```
手指向上滑动:
```
        //手指向上滑动
        GestureHelper.getInstance().verticalSlide(accessibilityService,896,300);
```
手指从右向左滑动:
```
        //手指从右向左滑动
        GestureHelper.getInstance().horizontalSlide(accessibilityService,980,100);
```
点击界面上的坐标点:
```
        //点击界面上的坐标点
        GestureHelper.getInstance().clickByGesture(accessibilityService,400,400);
```

