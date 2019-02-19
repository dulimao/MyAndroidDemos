package com.example.myandroidproject.android_libray.rxjava2.new_test.thread_switch;

import android.os.Handler;
import android.os.Looper;

import com.example.myandroidproject.android_libray.rxjava2.new_test.Observable;
import com.example.myandroidproject.android_libray.rxjava2.new_test.OnSubscribe;
import com.example.myandroidproject.android_libray.rxjava2.new_test.Subscrible;



public class OnSubscribleMain<T> implements OnSubscribe<T> {

    private Handler handler;
    Observable<T> source;

    public OnSubscribleMain(Handler handler,Observable<T> source){
        this.source = source;
        this.handler = handler;
    }


    @Override
    public void call(final Subscrible<? super T> subscrible) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                source.subscribe(subscrible);
            }
        });
    }
}
