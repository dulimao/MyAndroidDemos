package com.example.myandroidproject.architecture_component;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;

//生命周期监听，fragment可复用
public class BaseActivityLifecycle implements LifecycleObserver {


    private Context context;
    public BaseActivityLifecycle(Context context) {
        this.context = context;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        //do something
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        //do something
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        //do something
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        //do something
    }



}
