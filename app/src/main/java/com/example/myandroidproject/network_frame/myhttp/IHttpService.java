package com.example.myandroidproject.network_frame.myhttp;


/**
*@author 杜立茂
*@date 2018/12/28 9:37
*@description 请求体通用接口
*/
public interface IHttpService {
    void setUrl(String url);
    void setRequestData(byte[] data);
    void execute();
    void setHttpCallback(IHttpListener httpListener);
}
