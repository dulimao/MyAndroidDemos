package com.example.myandroidproject.network_frame;


import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
*@author 杜立茂
*@date 2018/12/27 19:34
*@description 网络请求被代理类
*/
public class OkHttpProcessor implements IHttpProcessor{

    private OkHttpClient okHttpClient;

    public OkHttpProcessor(){
        okHttpClient = new OkHttpClient();
    }



    @Override
    public void Post(String url, Map<String, Object> params, ICallback callback) {

    }

    @Override
    public void Get(String url, Map<String, Object> params, final ICallback callback) {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure("网络请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.onSuccess(response.body().string());
            }
        });
    }
}
