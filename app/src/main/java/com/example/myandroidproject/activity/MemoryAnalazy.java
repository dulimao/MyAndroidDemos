package com.example.myandroidproject.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myandroidproject.R;
import com.example.myandroidproject.utils.CommonUtil;

public class MemoryAnalazy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_analazy);
        CommonUtil.getInstance(this);
    }
}
