package com.example.myandroidproject.android_libray.rxjava2.new_test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.myandroidproject.R;
import com.example.myandroidproject.android_libray.rxjava2.new_test.map.Func1;

public class Test {


    //简单架构测试
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
        }).subscribleIO().subscribleMainThread().subscribe(new Subscrible<String>() {
            @Override
            public void onNext(String s) {
                Log.i("Rxjava_Test","string：" + s);
            }
        });
    }

    public static void test2(final Activity activity){
        Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscrible<? super String> subscrible) {
                subscrible.onNext("我想要一张ID对应的图片");
            }
        }).map(new Func1<String, Bitmap>() {
            @Override
            public Bitmap call(String s) {
                Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_launcher_background);

                return bitmap;
            }
        }).subscribe(new Subscrible<Bitmap>() {
            @Override
            public void onNext(Bitmap bitmap) {
                Log.i("Rxjava_Test","获取到bitmap 宽度:" + bitmap.getWidth() + "  长度： " + bitmap.getHeight());
            }
        });
    }
}
