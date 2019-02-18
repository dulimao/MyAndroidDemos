package com.example.myandroidproject.android_libray.rxjava2.new_test;

//观察者，订阅者抽象类
//T代表 观察者希望观察的项目类型
public abstract class Subscrible<T> {

    //得到被观察者发布的消息后做一些操作
    public abstract void onNext(T t);




}
