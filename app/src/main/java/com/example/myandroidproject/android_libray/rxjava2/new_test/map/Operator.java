package com.example.myandroidproject.android_libray.rxjava2.new_test.map;


import com.example.myandroidproject.android_libray.rxjava2.new_test.Subscrible;

//事件变换的抽象包装
//中间人的抽象
public interface Operator<T,R> extends Func1<Subscrible<? super T>,Subscrible<? super R>> {
}
