package com.example.myandroidproject.network_frame;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.myandroidproject.hotfix.FixDexUtil;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化HTTP,一键配置网络底层框架
//        HttpProxy.init(new VolleyProcessor(this));
        HttpProxy.init(new OkHttpProcessor());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        FixDexUtil.loadFixedDex(base);
        MultiDex.install(base);
    }
}
