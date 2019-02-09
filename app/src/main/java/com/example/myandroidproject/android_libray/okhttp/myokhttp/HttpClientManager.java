package com.example.myandroidproject.android_libray.okhttp.myokhttp;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClientManager {

    private OkHttpClient okHttpClient;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Gson gson;


    private HttpClientManager(){
        okHttpClient = new OkHttpClient().newBuilder().readTimeout(10000,TimeUnit.SECONDS)
                .connectTimeout(10000,TimeUnit.SECONDS).build();
        gson = new Gson();
    }

    private static class HttpClientManagerHoder{
        private static HttpClientManager instance = new HttpClientManager();
    }

    public static HttpClientManager getInstance(){
        return HttpClientManagerHoder.instance;
    }

    public void request(final HttpClient httpClient){
        Request request = new Request.Builder().get().url(httpClient.url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //TODO 根据返回的错误码进行相应处理
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpClient.callback.onFailure(0);
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String result = response.body().string();
                            if (httpClient.callback.mType == null || httpClient.callback.mType == String.class){
                                httpClient.callback.onSuccess(result);
                            }else {
                                httpClient.callback.onSuccess(gson.fromJson(result,httpClient.callback.mType));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });
    }
}
