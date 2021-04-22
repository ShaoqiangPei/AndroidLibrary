package com.android.commonlibrary.service.accessibility_service;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.android.commonlibrary.util.ScreenUtil;

/**
 * Title:无障碍服务手势帮助类
 * description:
 * autor:pei
 * created on 2021/4/19
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class GestureHelper {

    private static final long GESTURE_START_TIME=100L; //点击起始时间,单位毫秒
    private static final long GESTURE_DURATION = 500L; //点击延长时间,单位毫秒

    //屏幕尺寸
    private int mScreenWidth = ScreenUtil.getWidth();
    private int mScreenHeight = ScreenUtil.getHeight();

    //当前手机的实际屏幕宽高值
    private int mCurrentScreenWidth;
    private int mCurrentScreenHeight;

    private GestureHelper(){}

    private static class Holder {
        private static GestureHelper instance = new GestureHelper();
    }

    public static GestureHelper getInstance() {
        return Holder.instance;
    }

    /***
     * 设置屏幕尺寸
     *
     * @param currentScreenWidth 开发设备的实际屏幕宽度值
     * @param currentScreenHeight 开发设备的实际屏幕高度值
     * @return
     */
    public GestureHelper initSize(int currentScreenWidth,int currentScreenHeight){
        this.mCurrentScreenWidth=currentScreenWidth;
        this.mCurrentScreenHeight=currentScreenHeight;

        return GestureHelper.this;
    }

    /**手指竖直方向滑动**/
    public boolean verticalSlide(AccessibilityService service,
                                 int startY, int endY,
                                 long startTime,long duration){
        if(mCurrentScreenHeight==0){
            throw new SecurityException("=======请调用init(int currentScreenWidth,int currentScreenHeight)方法并传入当前手机设备的实际屏幕宽高值======");
        }

        float centerX =(float)(mScreenWidth/2);
        float starty =(float) (startY*mScreenHeight/mCurrentScreenHeight);
        float endy=(float)(endY*mScreenHeight/mCurrentScreenHeight);

        return AccessibilityHelper.getInstance().performGestureSliding(service,
                centerX, starty,
                centerX, endy,
                startTime,duration,
                null);
    }

    /**手指竖直方向滑动**/
    public boolean verticalSlide(AccessibilityService service,
                                 int startY, int endY) {
        return verticalSlide(service, startY, endY,
                GestureHelper.GESTURE_START_TIME, GestureHelper.GESTURE_DURATION);
    }

    /**手指水平方向滑动**/
    public boolean horizontalSlide(AccessibilityService service,
                                   int startX,int endX,
                                   long startTime,long duration){
        if(mCurrentScreenWidth==0){
            throw new SecurityException("=======请调用init(int currentScreenWidth,int currentScreenHeight)方法并传入当前手机设备的实际屏幕宽高值======");
        }

        float centerY=(float)(mScreenHeight/2);
        float startx=(float)(startX*mScreenWidth/mCurrentScreenWidth);
        float endx=(float)(endX*mScreenWidth/mCurrentScreenWidth);
        return AccessibilityHelper.getInstance().performGestureSliding(service,
                startx, centerY,
                endx, centerY,
                startTime,duration,
                null);
    }

    /**手指水平方向滑动**/
    public boolean horizontalSlide(AccessibilityService service,
                        int startX,int endX){
        return horizontalSlide(service,
                startX,endX,
                GestureHelper.GESTURE_START_TIME,GestureHelper.GESTURE_DURATION);
    }

    /**手势点击**/
    public boolean clickByGesture(AccessibilityService service, int x, int y,
                                  long startTime,long duration){
        if(mCurrentScreenWidth==0||mCurrentScreenHeight==0){
            throw new SecurityException("=======请调用init(int currentScreenWidth,int currentScreenHeight)方法并传入当前手机设备的实际屏幕宽高值======");
        }

        float tempX =(float)(x*mScreenWidth/mCurrentScreenWidth);
        float tempY=(float)(y*mScreenHeight/mCurrentScreenHeight);
        return AccessibilityHelper.getInstance().performClickByGesture(service,
                tempX,tempY,
                startTime,
                duration,null);
    }

    /**手势点击**/
    public boolean clickByGesture(AccessibilityService service, int x, int y){
        return clickByGesture(service,x,y,
                GestureHelper.GESTURE_START_TIME,GestureHelper.GESTURE_DURATION);
    }

}
