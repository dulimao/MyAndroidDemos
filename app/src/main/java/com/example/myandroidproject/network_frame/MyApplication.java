package com.example.myandroidproject.network_frame;

import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化HTTP,一键配置网络底层框架
//        HttpProxy.init(new VolleyProcessor(this));
        HttpProxy.init(new OkHttpProcessor());
    }


}
