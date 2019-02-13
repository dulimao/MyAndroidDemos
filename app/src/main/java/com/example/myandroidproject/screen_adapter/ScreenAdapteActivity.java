package com.example.myandroidproject.screen_adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.myandroidproject.R;

/**
*@author 杜立茂
*@date 2019/2/13 21:26
*@description 屏幕完全适配方案-100%适配
*/
public class ScreenAdapteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_adapte);
    }
}
