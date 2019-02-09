package com.example.myandroidproject.android_libray.eventbus;


import org.greenrobot.eventbus.EventBus;

/**
*@author 杜立茂
*@date 2018/12/22 10:05
*@description EventBus源码分析：订阅，发布，注解，反射
 *
*/
public class EventBusMain {


    public void test(){

        //1.注册 getDefault()以单利形式返回eventbus对象，
        // register方法则用反射或注解器将本类中订阅的方法添加到eventbus列表中
        EventBus.getDefault().register(this);
    }

}
