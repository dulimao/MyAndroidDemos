package com.example.myandroidproject.network_frame.myhttp;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

public class HttpTask implements Runnable{

    private IHttpListener httpListener;
    private IHttpService httpService;
    private Gson gson;

    public <T> HttpTask(String url,T requestInfo,IHttpService httpService,IHttpListener httpListener){
        gson = new Gson();
        this.httpService = httpService;
        this.httpListener = httpListener;
        httpService.setUrl(url);
        httpService.setHttpCallback(httpListener);
        if (requestInfo != null){
            String requestContent = gson.toJson(requestInfo);
            try {
                httpService.setRequestData(requestContent.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void run() {
        httpService.execute();
    }
}
