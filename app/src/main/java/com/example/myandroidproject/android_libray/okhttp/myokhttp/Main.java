package com.example.myandroidproject.android_libray.okhttp.myokhttp;

import com.example.myandroidproject.android_libray.okhttp.myokhttp.model.User;


public class Main {

    public static void main(String[] args){

        HttpClient.newBuilder().get().url("").build().enqueue(new BaseCallBack<User>() {
            @Override
            public void onSuccess(User user) {

            }

            @Override
            public void onFailure(int errorCode) {

            }
        });

        HttpClient.newBuilder().build().enqueue(new BaseCallBack() {
            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onFailure(int errorCode) {

            }
        });
    }
}
