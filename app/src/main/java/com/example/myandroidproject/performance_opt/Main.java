package com.example.myandroidproject.performance_opt;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

/**
*@author 杜立茂
*@date 2019/2/14 16:47
*@description 性能优化专题：
 *
*/
public class Main {



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
