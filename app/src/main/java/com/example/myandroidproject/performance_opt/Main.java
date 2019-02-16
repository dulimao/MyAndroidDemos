package com.example.myandroidproject.performance_opt;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.myandroidproject.R;

/**
*@author 杜立茂
*@date 2019/2/14 16:47
*@description 性能优化专题：
 *
*/
public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_opt);
        Log.e("Main","onCreate()");
    }


    public void testSingleton(View view){
        Singleton.getInstance(this).print();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Main","onDestroy()");
    }

    private void test(Context context){

        Runtime rt=Runtime.getRuntime();
        long maxMemory=rt.maxMemory();
        Log.i("JNITestActivity:","最大内存：" + Long.toString(maxMemory/(1024*1024)));
        Log.i("JNITestActivity:","total内存：" + Long.toString(rt.totalMemory()/(1024*1024)));
        Log.i("JNITestActivity:","空闲内存：" + Long.toString(rt.freeMemory()/(1024*1024)));

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        Log.i("JNITestActivity","系统剩余内存:"+(memoryInfo.availMem >> 10)+"k");
        Log.i("JNITestActivity","系统是否处于低内存运行："+memoryInfo.lowMemory);
        Log.i("JNITestActivity","当系统剩余内存低于"+memoryInfo.threshold+"时就看成低内存运行");
    }

}
