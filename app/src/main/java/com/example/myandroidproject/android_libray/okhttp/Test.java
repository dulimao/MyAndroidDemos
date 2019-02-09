package com.example.myandroidproject.android_libray.okhttp;

import android.app.Activity;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
*@author 杜立茂
*@date 2019/1/4 10:54
*@description 使用的设计模式：建造者模式，责任链模式，生产者消费者模式（分发器），策略模式和工厂模式（缓存拦截器）
*/
public class Test{

    public static void main(String[] args){
        synRequest();
    }


    /**
     * 同步请求
     */
    public static void synRequest(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(new File("cache"),24 * 1024 * 1024))//设置缓存策略
                .readTimeout(5, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url("http://www.baidu.com").get().build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();//进入阻塞状态，只到收到结果
            String result = response.body().toString();
            System.out.print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 异步请求
     */
    public void asynRequest(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url("http://www.baidu.com").get().build();
        Call call = okHttpClient.newCall(request);
        call.cancel();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //工作线程中
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //工作线程中

            }
        });
    }

}
