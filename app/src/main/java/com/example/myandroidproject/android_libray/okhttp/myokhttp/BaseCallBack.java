package com.example.myandroidproject.android_libray.okhttp.myokhttp;

import java.lang.reflect.Type;

public abstract class BaseCallBack<T> {

    public Type mType;

    public BaseCallBack(){
        //TODO 解析类型
    }

    abstract void onSuccess(T t);
    abstract void onFailure(int errorCode);
}
