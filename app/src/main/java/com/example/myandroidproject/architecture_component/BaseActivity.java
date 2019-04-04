package com.example.myandroidproject.architecture_component;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    Lifecycle lifecycle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindActivity());
        lifecycle.addObserver(new BaseActivityLifecycle(this));
    }

    protected abstract int bindActivity();
}
