package com.example.myandroidproject.network_frame;


import java.util.HashMap;
import java.util.Map;

/**
*@author 杜立茂
*@date 2018/12/27 19:32
*@description 网络请求代理类(静态代理),单例模式
*/
public class HttpProxy implements IHttpProcessor{

    private static IHttpProcessor mIHttpProcessor = null;

    private Map<String,Object> mParams;

    private static HttpProxy instance;

    private HttpProxy(){
        mParams = new HashMap<>();
    }

    public static HttpProxy obtain(){
        synchronized (HttpProxy.class){
            if (instance == null){
                synchronized (HttpProxy.class){
                    instance = new HttpProxy();
                }
            }
        }
        return instance;
    }


    public static void init(IHttpProcessor processor){
        mIHttpProcessor = processor;
    }

    @Override
    public void Post(String url, Map<String, Object> params, ICallback callback) {
        String finalUrl = appenNewUrl(url,params);
        mIHttpProcessor.Post(finalUrl,params,callback);
    }

    @Override
    public void Get(String url, Map<String, Object> params, ICallback callback) {
//        String finalUrl = appenNewUrl(url,params);
        mIHttpProcessor.Get(url,params,callback);
    }


    /**
     * url拼接
     * @param url
     * @param objectMap
     * @return
     */
    private String appenNewUrl(String url,Map<String,Object> objectMap){
        return url;
    }
}
