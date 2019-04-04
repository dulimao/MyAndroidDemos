package com.example.myandroidproject.architecture_component;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myandroidproject.R;

/**
 * UI展示类，负责数据展示和UI交互
 */
public class UserProfileActivity extends AppCompatActivity {

    private static final int UID_KEY = 110;
    private UserProfileViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        viewModel = ViewModelProviders.of(this).get(UserProfileViewModel.class);
        viewModel.init(UID_KEY);
    }


    /**
     * 观察数据并更新UI
     */
    public void updateUIByUese() {
        viewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                //根据user状态来更新UI
            }
        });
    }
}
