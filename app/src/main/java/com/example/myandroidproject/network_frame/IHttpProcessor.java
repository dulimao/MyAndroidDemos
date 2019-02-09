package com.example.myandroidproject.network_frame;


import java.util.Map;

/**
*@author 杜立茂
*@date 2018/12/27 19:29
*@description 网络请求代理规范接口
*/
public interface IHttpProcessor {
    //网络请求方式 POST,GET,DELETE,UPDATE,PUT

    void Post(String url, Map<String,Object> params,ICallback callback);

    void Get(String url,Map<String,Object> params,ICallback callback);
}
