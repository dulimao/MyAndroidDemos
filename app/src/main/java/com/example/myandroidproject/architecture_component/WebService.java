package com.example.myandroidproject.architecture_component;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

//Retrofit接口
public interface WebService {
    @GET("/user/{user}")
    Call<User> getUser(@Path("user") int userId);
}
