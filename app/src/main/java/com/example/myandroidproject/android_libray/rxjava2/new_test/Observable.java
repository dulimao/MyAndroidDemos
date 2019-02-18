package com.example.myandroidproject.android_libray.rxjava2.new_test;

//被观察者，
public class Observable<T> {

    private OnSubscribe<T> onSubscribe;

    public Observable(OnSubscribe onSubscribe) {
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
}
