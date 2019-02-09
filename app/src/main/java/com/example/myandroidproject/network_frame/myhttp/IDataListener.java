package com.example.myandroidproject.network_frame.myhttp;


/**
*@author 杜立茂
*@date 2018/12/28 10:23
*@description 用于回传结果给调用层
*/
public interface IDataListener<T> {
    void onSuccess(T data);
    void onFailure();
}
