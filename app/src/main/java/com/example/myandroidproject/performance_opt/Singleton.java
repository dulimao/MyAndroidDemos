package com.example.myandroidproject.performance_opt;

import android.content.Context;
import android.util.Log;

import dalvik.system.BaseDexClassLoader;

//单例造成内存泄漏

public class Singleton {

    private Context mContext;

    private static Singleton instance;

    private Singleton(Context context){
        this.mContext = context;
    }

    public static Singleton getInstance(Context context){
        if (instance == null){
            instance = new Singleton(context);
        }
        return instance;
    }

    public void print(){

        Log.e("Singleton","hell world");
    }

}
