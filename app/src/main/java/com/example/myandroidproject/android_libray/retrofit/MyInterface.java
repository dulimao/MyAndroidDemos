package com.example.myandroidproject.android_libray.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyInterface {
    @GET(".../...")
    Call<List<MyResponse>> getCall();
}
