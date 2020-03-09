package com.android.commonlibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;
import com.android.commonlibrary.R;

/**
 * Title:自定义进度条
 * description:
 * autor:pei
 * created on 2020/3/9
 */
public class LoadProgressBar extends ProgressBar {

    private static final int DEFAULT_TEXT_SIZE=12;//默认文字大小(单位sp)
    private static final int DEFAULT_TEXT_COLOR=0xFF000000;//默认文字颜色为黑色
    private static final int DEFAULT_COLOR_UNREACH=0xFFA6ADB7;//默认未完成进度条颜色
    private static final int DEFAULT_HEIGHT_UNREACH=5;//默认未完成进度条高度(单位dp)
    private static final int DEFAULT_COLOR_REACH=0xFF8AD858;//默认已完成进度条颜色
    private static final int DEFAULT_HEIGHT_REACH=DEFAULT_HEIGHT_UNREACH;//默认已完成进度条高度(单位dp)
    private static final int DEFAULT_TEXT_OFFSET=10;//默认文字与进度条间距(单位dp)
    private static final boolean DEFAULT_CIRCLE_STYLE=false;//是否为圆形样式,默认为false,即水平样式
    private static final int DEFAULT_RADIUS=30;//默认半径(单位dp)

    private int mTextSize=sp2px(DEFAULT_TEXT_SIZE);
    private int mTextColor=DEFAULT_TEXT_COLOR;
    private int mUnReachColor=DEFAULT_COLOR_UNREACH;
    private int mUnReachHeight=dp2px(DEFAULT_HEIGHT_UNREACH);
    private int mReachColor=DEFAULT_COLOR_REACH;
    private int mReachHeight=dp2px(DEFAULT_HEIGHT_REACH);
    private int mTextOffset=dp2px(DEFAULT_TEXT_OFFSET);
    private boolean mCircleStyle=DEFAULT_CIRCLE_STYLE;//是否为圆形样式,默认为false,即水平样式

    private Paint mPaint;
    private int mRealWidth;//实际绘制宽度

    //为圆形样式时
    private int mRadius=dp2px(DEFAULT_RADIUS);//绘制圆形半径
    private int maxPaintWidth;//绘制最大宽度

    public LoadProgressBar(Context context) {
        this(context,null);
    }

    public LoadProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //初始化加载自定义属性
        initAttrs(attrs);
        //初始化画笔Paint
        initPaint();
    }

    /**初始化加载自定义属性**/
    public void initAttrs(AttributeSet attrs) {
        TypedArray typedArray=getContext().obtainStyledAttributes(attrs, R.styleable.LoadProgressBar);
        mTextSize= (int) typedArray.getDimension(R.styleable.LoadProgressBar_progress_text_size,mTextSize);
        mTextColor= typedArray.getColor(R.styleable.LoadProgressBar_progress_text_color,mTextColor);
        mUnReachColor=typedArray.getColor(R.styleable.LoadProgressBar_progress_unreach_color,mUnReachColor);
        mUnReachHeight= (int) typedArray.getDimension(R.styleable.LoadProgressBar_progress_unreach_height,mUnReachHeight);
        mReachColor=typedArray.getColor(R.styleable.LoadProgressBar_progress_reach_color,mReachColor);
        mReachHeight= (int) typedArray.getDimension(R.styleable.LoadProgressBar_progress_reach_height,mReachHeight);
        mTextOffset= (int) typedArray.getDimension(R.styleable.LoadProgressBar_progress_text_offset,mTextOffset);
        mCircleStyle= typedArray.getBoolean(R.styleable.LoadProgressBar_progress_circle_style,mCircleStyle);
        mRadius= (int) typedArray.getDimension(R.styleable.LoadProgressBar_progress_radius,mRadius);

        if(mCircleStyle){//圆形进度条
            mReachHeight= (int) (mUnReachHeight*1.5);
        }
        typedArray.recycle();
    }

    /***
     * 更新进度条
     * @param progress 进度
     * @return true:已经更新到最大,需要移除handle消息  false:进度条更新
     */
    public boolean update(int progress){
        boolean complete=false;
        if(progress<=getMax()) {
            this.setProgress((int) (progress * 1.0f));
        }else{
            this.setProgress(getMax());
            complete=true;
        }
        return complete;
    }

    /**初始化画笔Paint**/
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        if(mCircleStyle) {//圆形进度条
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
        }
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(mCircleStyle){//圆形进度条
            meatureRoundProgress(widthMeasureSpec, heightMeasureSpec);
        }else {//水平进度条
            meatureHorizontalProgress(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**测量圆形进度条**/
    private void meatureRoundProgress(int widthMeasureSpec, int heightMeasureSpec) {
        maxPaintWidth= Math.max(mReachHeight,mUnReachHeight);
        //默认四个padding一致
        int expect=mRadius*2+maxPaintWidth+getPaddingLeft()+getPaddingRight();

        int width=resolveSize(expect,widthMeasureSpec);
        int height=resolveSize(expect,heightMeasureSpec);

        int realWidth= Math.min(width,height);
        mRadius=(realWidth-getPaddingLeft()-getPaddingRight()-maxPaintWidth)/2;
        setMeasuredDimension(realWidth,realWidth);
    }

    /**测量水平进度条**/
    private void meatureHorizontalProgress(int widthMeasureSpec, int heightMeasureSpec) {
        //int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        //测量完宽高一定要调用
        setMeasuredDimension(width, height);
        //获取实际绘制宽度
        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    /**测量高度**/
    private int measureHeight(int heightMeasureSpec) {
        int height=0;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int size= MeasureSpec.getSize(heightMeasureSpec);

        //MeasureSpec.EXACTLY:精确测量模式
        //MeasureSpec.UNSPECIFIED:就是未指定的意思,在这个模式下父控件不会干涉子View想要多大的尺寸
        //MeasureSpec.AT_MOST:你的尺寸不能超出你所在的ViewGroup的尺寸
        switch (heightMode) {
            case MeasureSpec.EXACTLY://精确测量模式
                height = size;
                break;
            default:
                int textHeight = (int) (mPaint.descent() - mPaint.ascent());
                height = getPaddingTop() + getPaddingBottom() + Math.max(Math.max(mReachHeight, mUnReachHeight), Math.abs(textHeight));
                if(heightMode== MeasureSpec.AT_MOST){
                    height= Math.min(height,size);
                }
                break;
        }
        return height;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        //完全重写onDraw,不需要调用父类super方法

        if(mCircleStyle){//圆形进度条
            drawRoundProgress(canvas);
        }else {//水平进度条
            drawHorizontalProgress(canvas);
        }

    }

    /**绘制圆形进度条**/
    private void drawRoundProgress(Canvas canvas) {
        String text=getProgress()+"%";
        float textWidth=mPaint.measureText(text);
        float textHeight=(mPaint.descent()+mPaint.ascent())/2;

        canvas.save();
        canvas.translate(getPaddingLeft()+maxPaintWidth/2,getPaddingTop()+maxPaintWidth/2);
        mPaint.setStyle(Paint.Style.STROKE);
        //绘制未加载部分
        mPaint.setColor(mUnReachColor);
        mPaint.setStrokeWidth(mUnReachHeight);
        canvas.drawCircle(mRadius,mRadius,mRadius,mPaint);
        //绘制已加载部分
        mPaint.setColor(mReachColor);
        mPaint.setStrokeWidth(mReachHeight);
        float sweepAngle=getProgress()*1.0f/getMax()*360;
        canvas.drawArc(new RectF(0,0,mRadius*2,mRadius*2),0,sweepAngle,false,mPaint);
        //绘制文字
        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text,mRadius-textWidth/2,mRadius-textHeight,mPaint);
        canvas.restore();
    }

    /**绘制水平进度条**/
    private void drawHorizontalProgress(Canvas canvas) {
        canvas.save();
        //设置绘制初始位置为中心点
        canvas.translate(getPaddingLeft(),getHeight()/2);
        boolean noNeedUnReach=false;
        //绘制reach bar
        String text=getProgress()+"%";
        int textWidth= (int) mPaint.measureText(text);
        float radio=getProgress()*1.0f/getMax();
        float progressX=radio*mRealWidth;
        //若已完成长度+文字宽度大于整个宽度,则progressX显示为整个宽度-文字宽度并不再改变
        if(progressX+textWidth>mRealWidth){
            progressX=mRealWidth-textWidth;
            noNeedUnReach=true;
        }
        float endX=progressX-mTextOffset/2;
        if(endX>0){
            mPaint.setColor(mReachColor);
            mPaint.setStrokeWidth(mReachHeight);
            canvas.drawLine(0,0,endX,0,mPaint);
        }

        //绘制文字
        mPaint.setColor(mTextColor);
        int y= (int) -(mPaint.descent()+mPaint.ascent()/2);
        canvas.drawText(text,progressX,y,mPaint);

        //绘制unReach bar
        if(!noNeedUnReach){
            float start=progressX+mTextOffset/2+textWidth;
            mPaint.setColor(mUnReachColor);
            mPaint.setStrokeWidth(mUnReachHeight);
            canvas.drawLine(start,0,mRealWidth,0,mPaint);
        }
        canvas.restore();
    }


    /**dp转px**/
    private int dp2px(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
    }

    /**sp转dx**/
    private int sp2px(int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }

}
