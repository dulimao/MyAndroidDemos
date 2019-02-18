package com.example.myandroidproject.android_libray.rxjava2.new_test;

import android.util.Log;

public class Test {

    public static void test(){
        //根据事件创建一个观察者对象
        Observable.create(new OnSubscribe<String>() {
            @Override//事件中包含了订阅者信息
            public void call(Subscrible<? super String> subscrible) {
                //do something
                //拿到订阅者的引用并调用其方法
                subscrible.onNext("hello 树先生");
                subscrible.onNext("我是你想要的结果");
            }
            //观察者和订阅者进行订阅关联
        }).subscribe(new Subscrible<String>() {
            @Override
            public void onNext(String s) {
                Log.i("Rxjava Test","string：" + s);
            }
        });
    }
}
