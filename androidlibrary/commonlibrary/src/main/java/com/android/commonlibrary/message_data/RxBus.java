package com.android.commonlibrary.message_data;

import android.util.Log;
import com.android.commonlibrary.entity.RxData;
import java.util.HashMap;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Instruction: RxBus 基于 RxJava2.x 实现事务总线
 *
 * Author:pei
 * Date: 2017/7/25
 * Description:
 */
public class RxBus {

    private static HashMap<String, Disposable> mSubscriptionMap=new HashMap<>() ;
    private static volatile RxBus mRxBus;
    private final Subject<Object> mSubject;

    //单列模式
    public static RxBus getInstance(){
        if (mRxBus==null){
            synchronized (RxBus.class){
                if(mRxBus==null){
                    mRxBus = new RxBus();
                }
            }
        }
        return mRxBus;
    }
    public RxBus(){
        mSubject = PublishSubject.create().toSerialized();
    }

    /**
     * 返回指定类型的带背压的Flowable实例
     *
     * @param <T>
     * @param type
     * @return
     */
    private <T> Flowable<T> getObservable(Class<T> type){
        return mSubject.toFlowable(BackpressureStrategy.BUFFER)
                .ofType(type);
    }
    /**
     * 一个默认的订阅方法
     *
     * @param <T>
     * @param type
     * @param next
     * @param error
     * @return
     */
    private <T> Disposable doSubscribe(Class<T> type, Consumer<T> next, Consumer<Throwable> error){
        return getObservable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next,error);
    }
    /**
     * 是否已有观察者订阅
     *
     * @return
     */
    public boolean hasObservers() {
        return mSubject.hasObservers();
    }

    /**
     * 保存订阅后的disposable
     * @param o
     * @param disposable
     */
    private void addSubscription(Object o, Disposable disposable) {
        String key = o.toString();
        if(mSubscriptionMap.containsKey(key)){
            mSubscriptionMap.get(key).dispose();
            mSubscriptionMap.remove(key);
        }
        mSubscriptionMap.put(key,disposable);
    }

    /**
     * 取消订阅
     * @param o
     */
    private void unSubscribe(Object o) {
        if (mSubscriptionMap == null) {
            return;
        }
        String key = o.toString();
        if (!mSubscriptionMap.containsKey(key)){
            return;
        }
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).dispose();
        }
        mSubscriptionMap.remove(key);
    }

    private <T> void registerRxBus(Class<?>cls, Class<RxData> eventType, Consumer<RxData> action) {
        String className=cls.getName();
        Disposable disposable = mRxBus.doSubscribe(eventType, action, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e("NewsMainPresenter", throwable.toString());
            }
        });
        mRxBus.addSubscription(className,disposable);
    }


    public interface OnCallBack{
        void callBack(Object obj);
    }

    /**发送数据**/
    public void post(Object o){
        mSubject.onNext(o);
    }

    /***
     * 接收消息，外部调用(一般放在setListener中，类似button监听的用法)
     * @param onCallBack
     */
    public void register(Class<?>cls, final OnCallBack onCallBack){
        registerRxBus(cls,RxData.class, new Consumer<RxData>() {
            @Override
            public void accept(@NonNull RxData rxData) throws Exception {
                if(onCallBack!=null){
                    onCallBack.callBack(rxData);
                }
            }
        });
    }

    /**注销**/
    public void unRegister(Class<?>cls) {
        String className=cls.getName();
        mRxBus.unSubscribe(className);
    }

}


