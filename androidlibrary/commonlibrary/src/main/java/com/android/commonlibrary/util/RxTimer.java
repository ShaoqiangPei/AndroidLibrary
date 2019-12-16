package com.android.commonlibrary.util;

import androidx.annotation.NonNull;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Title: RxJava2.x实现的定时器
 * description:
 * autor:pei
 * created on 2019/12/16
 */
public class RxTimer {

    private Disposable mDisposable;
    //tag用于标识不同的定时器
    private String mTag;

    public RxTimer(){}

    public RxTimer(String tag){
        this.mTag=tag;
    }

    /**获取定时器tag**/
    public String getTag() {
        return mTag;
    }

    /**设置定时器tag**/
    public void setTag(String tag) {
        this.mTag = tag;
    }

    /** milliseconds毫秒后执行next操作
     *
     * @param milliseconds：毫秒
     * @param next
     */
    public void timer(long milliseconds,final OnRxTimeListener next) {
        Observable.timer(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable=disposable;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if(next!=null){
                            next.next(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //取消订阅
                        cancel();
                    }

                    @Override
                    public void onComplete() {
                        //取消订阅
                        cancel();
                    }
                });
    }


    /** 每隔milliseconds毫秒后执行next操作
     *
     * @param milliseconds：毫秒
     * @param next
     */
    public void interval(long milliseconds,final OnRxTimeListener next){
        Observable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable=disposable;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if(next!=null){
                            next.next(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 取消订阅
     */
    public void cancel(){
        if(mDisposable!=null&&!mDisposable.isDisposed()){
            mDisposable.dispose();

            LogUtil.i("========RxTimer定时器注销======tag="+getTag());
        }
    }

    public interface OnRxTimeListener{
        void next(long number);
    }

}
