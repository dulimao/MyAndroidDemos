package com.example.myandroidproject.network_frame;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
*@author 杜立茂
*@date 2018/12/27 19:27
*@description 网络请求结果抽象类
*/
public abstract class HttpCallback<T> implements ICallback {



    @Override
    public void onSuccess(String data) {
        Gson gson = new Gson();
        //获取泛型T的字节码对象
        Class<?> clazz = analysisClassInfo(this);
        T result = (T) gson.fromJson(data,clazz);
        onSuccess(result);
    }


    public abstract void onSuccess(T t);



    private Class<?> analysisClassInfo(Object object){
        Type type = object.getClass().getGenericSuperclass();
        Type[] paramsTypes = ((ParameterizedType) type).getActualTypeArguments();
        return (Class<?>) paramsTypes[0];
    }
}
