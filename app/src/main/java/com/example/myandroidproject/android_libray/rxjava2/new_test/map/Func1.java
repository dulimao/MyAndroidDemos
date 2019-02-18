package com.example.myandroidproject.android_libray.rxjava2.new_test.map;

//事件变换的抽象行为 目的：T-》R
public interface Func1<T,R> {
    R call(T t);
}
