package com.example.myandroidproject.android_libray.rxjava2.new_test.thread_switch;

import com.example.myandroidproject.android_libray.rxjava2.new_test.Observable;
import com.example.myandroidproject.android_libray.rxjava2.new_test.OnSubscribe;
import com.example.myandroidproject.android_libray.rxjava2.new_test.Subscrible;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//线程切换中间类
public class OnSubscribleOnIO<T> implements OnSubscribe<T> {
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    Observable<T> source;

    public OnSubscribleOnIO(Observable<T> source) {
        this.source = source;
    }

    @Override
    public void call(final Subscrible<? super T> subscrible) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                source.subscribe(subscrible);
            }
        };
        executorService.submit(runnable);
    }
}































