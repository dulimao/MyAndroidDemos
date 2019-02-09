package com.example.myandroidproject.network_frame.myhttp;


import java.io.InputStream;

/**
*@author 杜立茂
*@date 2018/12/28 9:38
*@description 相应体通用接口
*/
public interface IHttpListener {
    void onSuccess(InputStream inputStream);
    void onFailure();
}
