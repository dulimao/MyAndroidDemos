package com.example.myandroidproject.android_libray.rxjava2.bean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    @GET(".../...")
    Call<String> getUserInfo();
}
