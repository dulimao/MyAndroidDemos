package com.example.myandroidproject.performance_opt;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.example.myandroidproject.R;

import java.util.Arrays;
import java.util.Random;

/**
*@author 杜立茂
*@date 2019/2/14 16:47
*@description 性能优化专题：
 *
 *
*/
public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_opt);
        Log.e("Main","onCreate()");

        WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl("file:///android_asset/shiver_me_timbers.gif");
    }


    public void testSingleton(View view){
        Singleton.getInstance(this).print();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Main","onDestroy()");
    }


    //------------------------UI优化 start-------------------------\\
    //----------------------案例一：内存抖动测试---------------------------//
    public void memeory_test(View view) {
        imPrettySureSortingIsFree();

    }
    /**
     * 案例1:
     * TODO: Warning:GC会不停的回收垃圾内存，造成内存严重抖动,因为数组是分配到堆区上的
     *　排序后打印二维数组，一行行打印
     */
    public void imPrettySureSortingIsFree() {
        int dimension = 300;
        int[][] lotsOfInts = new int[dimension][dimension];
        Random randomGenerator = new Random();
        for(int i = 0; i < lotsOfInts.length; i++) {
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                lotsOfInts[i][j] = randomGenerator.nextInt();
            }
        }

        for(int i = 0; i < lotsOfInts.length; i++) {
            String rowAsStr = "";
            //排序
            int[] sorted = getSorted(lotsOfInts[i]);
            //拼接打印
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                rowAsStr += sorted[j];
                if(j < (lotsOfInts[i].length - 1)){
                    rowAsStr += ", ";
                }
            }
            Log.i("ricky", "Row " + i + ": " + rowAsStr);
        }

        //优化以后
//        StringBuilder sb = new StringBuilder();
//        String rowAsStr = "";
//        for(int i = 0; i < lotsOfInts.length; i++) {
//            //清除上一行
//            sb.delete(0,rowAsStr.length());
//            //排序
//            int[] sorted = getSorted(lotsOfInts[i]);
//            //拼接打印
//            for (int j = 0; j < lotsOfInts[i].length; j++) {
////                rowAsStr += sorted[j];
//                sb.append(sorted[j]);
//                if(j < (lotsOfInts[i].length - 1)){
////                    rowAsStr += ", ";
//                    sb.append(", ");
//                }
//            }
//            rowAsStr = sb.toString();
//            Log.i("ricky", "Row " + i + ": " + rowAsStr);
//        }


    }

    public int[] getSorted(int[] input){
        int[] clone = input.clone();
        Arrays.sort(clone);
        return clone;
    }


    //-----------------------案例二-----------------//


    public void cpu_test(View view) {
        Log.e("Main cpu_test","cpu_test start" );
        long startTime = System.currentTimeMillis();
        //todo 大于等于50直接卡死，程序无响应
        computeFibonacci(50);
        long endTime = System.currentTimeMillis();
        Log.e("Main cpu_test","cpu_test time: " + (endTime - startTime));
    }

    /**
     * 递归算法-严重消耗CPU资源，导致UI卡顿
     * @param positionInFibSequence
     * @return
     */
    public int computeFibonacci(int positionInFibSequence) {
        //0 1 1 2 3 5 8
        if (positionInFibSequence <= 2) {
            return 1;
        } else {
            return computeFibonacci(positionInFibSequence - 1)
                    + computeFibonacci(positionInFibSequence - 2);
        }
    }

    //优化后的斐波那契数列的非递归算法 caching缓存+批处理思想，空间换时间
    public int computeFibonacci1(int positionInFibSequence) {
        int prev = 0;
        int current = 1;
        int newValue;
        for (int i=1; i<positionInFibSequence; i++) {
            newValue = current + prev;
            prev = current;
            current = newValue;
        }
        return current;
    }


    //------------------------UI优化 end-------------------------\\

    /**
     * 打印内存使用情况
     * @param context
     */
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
