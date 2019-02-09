package com.example.myandroidproject.network_frame.myhttp;

public class Volley {

    public static <T,M> void sendJsonRequest(String url,T requestInfo,Class<M> response,IDataListener<T> listener){
        IHttpService httpService = new JsonHttpService();
        IHttpListener httpListener = new JsonHttpListener(response,listener);
        HttpTask httpTask = new HttpTask(url,requestInfo,httpService,httpListener);
        ThreadPoolManager.getInstance().execute(httpTask);
    }
}
