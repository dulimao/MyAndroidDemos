package com.example.myandroidproject.performance_opt.splash_fragment;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewStub;

import com.example.myandroidproject.R;

import java.lang.ref.WeakReference;


/**
 * 第一步优化时间： ThisTime: 395
 *                  TotalTime: 395
 *                  WaitTime: 418
 *
 * 第二步优化时间： ThisTime: 380
 *                  TotalTime: 380
 *                  WaitTime: 396
 */

public class M_Activity extends FragmentActivity {

    private Handler mHandler = new Handler();
    static ViewStub viewStub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_);

        final SplashFragment splashFragment = new SplashFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,splashFragment);
        transaction.commit();

        //1。窗体完全显示出来，再做延时加载M_Activity的布局，也就是SplashFragment
        //2。使用ViewStub加载真正的布局
        viewStub = findViewById(R.id.content_viewstub);
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                //开始延时加载

                mHandler.postDelayed(new DelayRunnable(M_Activity.this,splashFragment),2000);


               // mHandler.post(new DelayRunnable(M_Activity.this,splashFragment));
            }
        });

        //todo 可以同时异步加载数据
    }

    static class DelayRunnable implements Runnable{

        private WeakReference<Context> contextRef;
        private WeakReference<SplashFragment> fragmentRef;

        public DelayRunnable(Context context,SplashFragment splashFragment){
            contextRef = new WeakReference<>(context);
            fragmentRef = new WeakReference<>(splashFragment);
        }

        @Override
        public void run() {
            viewStub.inflate();
            //移除SplashFragment
            FragmentActivity fragmentActivity = (FragmentActivity) contextRef.get();
            SplashFragment fragment = fragmentRef.get();
            if (fragmentActivity != null && fragment != null) {
                FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
                transaction.remove(fragmentRef.get());
                transaction.commit();
            }
        }
    }
}
