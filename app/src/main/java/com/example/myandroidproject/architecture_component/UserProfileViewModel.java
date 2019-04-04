package com.example.myandroidproject.architecture_component;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

/**
 * 存储用户数据
 */
public class UserProfileViewModel extends ViewModel {

    private int userId;
    //private User user;
    private LiveData<User> user;
    private UserRepository userRepo;


    public UserProfileViewModel(UserRepository userRepo) {
        this.userRepo = userRepo;
    }


    public void init(int userId) {
        if (this.user != null)
            return;

        user = userRepo.getUser(userId);
    }

    public LiveData<User> getUser() {
        return user;
    }


}
