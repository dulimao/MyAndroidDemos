package com.example.myandroidproject.android_libray.rxjava2.new_test;

//extends 用于返回类型的限定
//super 用于参数类型的限定

/**
 * 事件
 * @param <T> 观察者
 */
public interface OnSubscribe<T> extends Action1<Subscrible<? super T>> {

}
