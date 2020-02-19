package com.android.commonlibrary.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.android.commonlibrary.util.LogUtil;
import com.android.commonlibrary.util.ScreenUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Title:表情雨控件
 * description:
 * autor:pei
 * created on 2020/2/15
 */
public class EmotionView extends View {

    //默认最大下落时间
    private static final int MAX_DURATION=2000;//2秒

    private Context mContext;
    private boolean isRaining;//是否开始表情雨
    private float mEmotionWidth;//表情宽度
    private float mEmotionHeight;//表情高度

    private Random mRandom;
    private List<Emotion> mEmotionList;
    private Matrix matrix;//用于缩放图标
    private Paint mPaint;

    private long mStartTimeStamp;
    //设置下落时间
    private int mDuration=MAX_DURATION;//默认两秒

    private OnRainListener mOnRainListener;

    public EmotionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;

        mEmotionList=new ArrayList<>();
        mRandom=new Random();
        matrix=new Matrix();
        mPaint=new Paint();
        mPaint.setColor(Color.RED);
    }

    /**
     * 设置时间
     * @param duration 单位毫秒，默认2000，即2秒
     */
    public EmotionView setDuration(int duration){
        if(duration<=0){
            throw new SecurityException("======设置下落时间必须大于0=====");
        }
        this.mDuration=duration;

        return EmotionView.this;
    }

    /**
     * 开始下表情雨
     * @param bitmapList 图片资源数组
     * @param listener 表情雨执行监听,设置为null时则不监听执行效果
     */
    public void startRain(List<Bitmap> bitmapList, OnRainListener listener){
        this.mOnRainListener=listener;
        if(mOnRainListener!=null){
            mOnRainListener.start();
        }
        resetRain();
        setVisibility(View.VISIBLE);
        initData(bitmapList);
        isRaining=true;
        invalidate();//整个控件执行onDraw
        LogUtil.i("========表情雨运行开始========");
    }

    /**重置表情雨(供外部调用,一般在界面ondestroy时调用)**/
    public void resetRain(){
        //设置控件隐藏
        setVisibility(View.GONE);
        isRaining=false;
    }

    /**表情雨监听**/
    public interface OnRainListener{
        void start();
        void stop();
    }

    /**结束表情雨**/
    private void stopRain(){
        if(mOnRainListener!=null){
            mOnRainListener.stop();
        }
        resetRain();
    }

    /**初始化数据bean**/
    private void initData(List<Bitmap> bitmapList) {
        //设置下落图标大小
        mEmotionWidth = ScreenUtil.dp2px(50, mContext);
        mEmotionHeight = ScreenUtil.dp2px(50, mContext);

        mEmotionList.clear();
        mStartTimeStamp= System.currentTimeMillis();

        int currrentDuration=0;
        int i=0;
        int size=bitmapList.size();
        while (currrentDuration<mDuration){
            Emotion emotion=new Emotion();
            //取余表示要是落下时间较长，那么图标会在bitmapList中循环取
            emotion.setBitmap(bitmapList.get(i%size));
            if(null==emotion.getBitmap()){
                LogUtil.i("=====设置表情雨的bitmap中含null的bitmap==i="+i);
            }
            //下落初始位置
            emotion.setX(mRandom.nextInt(ScreenUtil.getWidth()));
            //以图标高度左右在界面以外就行
            emotion.setY(-(int) Math.ceil(mEmotionHeight));//Math.ceil函数返回大于或等于一个给定数字的最小整数

            //下落速率
            float duration=mRandom.nextInt(500)+mDuration;
            //乘以16是因为Android界面每16毫秒刷新一次界面，这样会使界面运行平滑
            int velocityY= (int) (ScreenUtil.getHeight()*16/duration);
            emotion.setVelocityY(velocityY);
            int velocationX= Math.round(mRandom.nextFloat());
            emotion.setVelocityX(velocationX);

            //下落开始时间
            emotion.setAppearTimeStamp(currrentDuration);
            mEmotionList.add(emotion);

            //处理循环
            currrentDuration=currrentDuration+mRandom.nextInt(250);
            i++;
        }
        LogUtil.i("======表情雨mEmotionList长度==========="+i);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //若未开始下雨或emotion初始化失败则不执行绘制
        if (!isRaining || mEmotionList.isEmpty()) {
           return;
        }

        for (int i = 0; i < mEmotionList.size(); i++) {
            Emotion emotion=mEmotionList.get(i);
            //表情雨下落高度超出屏幕高度不执行绘制
            Bitmap bitmap=emotion.getBitmap();
            long totalTimeStamp= System.currentTimeMillis()-mStartTimeStamp;
            if(null==bitmap||bitmap.isRecycled()||emotion.getY()>ScreenUtil.getHeight()||totalTimeStamp<emotion.getAppearTimeStamp()){
                continue;
            }
            //缩放图标
            matrix.reset();
            float widthScale=mEmotionWidth/bitmap.getWidth();
            float heightScale=mEmotionHeight/bitmap.getHeight();
            matrix.setScale(widthScale,heightScale);

            //移动图标
            emotion.setX(emotion.getX()+emotion.getVelocityX());
            emotion.setY(emotion.getY()+emotion.getVelocityY());
            matrix.postTranslate(emotion.getX(),emotion.getY());

            //画cavas
            canvas.drawBitmap(bitmap,matrix,mPaint);
        }

        if(isEnd()){
            stopRain();
            LogUtil.i("========表情雨运行结束========");
        }else {
            //重绘
            postInvalidate();
            LogUtil.i("=====表情雨重绘=======");
        }
    }

    private boolean isEnd(){
        for (int i = 0; i < mEmotionList.size(); i++) {
            Emotion emotion=mEmotionList.get(i);
            if(emotion.getBitmap()!=null&&emotion.getY()<ScreenUtil.getHeight()){
                return false;
            }
        }
        return true;
    }

    /**数据类**/
    public class Emotion implements Serializable {

        private Bitmap bitmap;//需要绘制的bitmap
        private int x,y;//x,y轴坐标
        private int velocityX,velocityY;//x，y轴下落的速率

        private long appearTimeStamp;//目标开始下落时间

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getVelocityX() {
            return velocityX;
        }

        public void setVelocityX(int velocityX) {
            this.velocityX = velocityX;
        }

        public int getVelocityY() {
            return velocityY;
        }

        public void setVelocityY(int velocityY) {
            this.velocityY = velocityY;
        }

        public long getAppearTimeStamp() {
            return appearTimeStamp;
        }

        public void setAppearTimeStamp(long appearTimeStamp) {
            this.appearTimeStamp = appearTimeStamp;
        }
    }

}
