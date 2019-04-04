package com.example.myandroidproject.architecture_component;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//获取用户数据

public class UserRepository {

    private WebService webService;
    private final UserDao userDao;
    private final Executor executor;

    public UserRepository(WebService webService,UserDao userDao,Executor executor) {
        this.webService = webService;
        this.userDao = userDao;
        this.executor = executor;
    }



    public LiveData<User> getUser(int userId) {


        refreshUser(userId);

        return userDao.load(userId);
//
//        //从网络获取
//        final MutableLiveData<User> data = new MutableLiveData<>();
//        webService.getUser(userId).enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                data.setValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                User user = new User();
//                user.setUserName("dulimao");
//                user.setAge(22);
//                user.setUserID("110");
//                data.setValue(new User());
//            }
//        });
//
//        return data;
    }


    private void refreshUser(final int userId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                boolean userExists = false;
                if (!userExists) {
                    try {
                        Response response = webService.getUser(userId).execute();
                        userDao.save((User) response.body());
                    } catch (IOException e) {

                    }
                }
            }
        });
    }
}
