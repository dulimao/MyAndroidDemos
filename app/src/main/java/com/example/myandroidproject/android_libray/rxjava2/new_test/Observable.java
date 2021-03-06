package com.example.myandroidproject.android_libray.rxjava2.new_test;

import android.os.Handler;
import android.os.Looper;

import com.example.myandroidproject.android_libray.rxjava2.new_test.map.Func1;
import com.example.myandroidproject.android_libray.rxjava2.new_test.map.OnSubscribeLift;
import com.example.myandroidproject.android_libray.rxjava2.new_test.map.Operator;
import com.example.myandroidproject.android_libray.rxjava2.new_test.map.OperatorMap;
import com.example.myandroidproject.android_libray.rxjava2.new_test.thread_switch.OnSubscribleMain;
import com.example.myandroidproject.android_libray.rxjava2.new_test.thread_switch.OnSubscribleOnIO;

//被观察者，
public class Observable<T> {

    //持有事件的引用
    private OnSubscribe<T> onSubscribe;

    public Observable(OnSubscribe<T> onSubscribe) {
        this.onSubscribe = onSubscribe;
    }

    //根据事件创建被观察者对象
    public static <T> Observable<T> create(OnSubscribe<T> onSubscribe){
        return new Observable<>(onSubscribe);
    }

    //订阅
    public void subscribe(Subscrible<? super T> subscrible){
        onSubscribe.call(subscrible);
    }


    public <R> Observable<R> map(Func1<? super T,? extends R> func){
        return lift(new OperatorMap<T,R>(func));
    }

    public <R> Observable<R> lift(OperatorMap<T,R> trOperator){
        return new Observable<>(new OnSubscribeLift(onSubscribe,trOperator));
    }


    //子线程切换线程切换
    public Observable<T> subscribleIO(){
        return create(new OnSubscribleOnIO<T>(this));
    }

    //主线程切换
    public Observable<T> subscribleMainThread(){
        return create(new OnSubscribleMain<T>(new Handler(Looper.getMainLooper()),this));
    }



}
