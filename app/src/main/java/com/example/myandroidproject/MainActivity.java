package com.example.myandroidproject;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.myandroidproject.activity.MemoryAnalazy;
import com.example.myandroidproject.android_libray.rxjava2.RXJavaActivity;
import com.example.myandroidproject.network_frame.HttpCallback;
import com.example.myandroidproject.network_frame.HttpProxy;
import com.example.myandroidproject.network_frame.Person;
import com.example.myandroidproject.network_frame.myhttp.IDataListener;
import com.example.myandroidproject.network_frame.myhttp.Volley;
import com.example.myandroidproject.view.EventActivity;
import com.squareup.picasso.Picasso;

import java.util.LinkedHashMap;
import java.util.WeakHashMap;

public class MainActivity extends Activity {

    Handler workHandler;
    HandlerThread handlerThread;




    private static class Student{}




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Student student = new Student();

        handlerThread = new HandlerThread("handlerthread");
        handlerThread.start();

//        Glide.with(this).load("").into();
        WeakHashMap hashMap;
//        LinkedHashMap

//        Picasso.get().load("").into();

//        Glide.with(this).load("").into();
        RecyclerView recyclerView;


        workHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //处理耗时操作
            }
        };

        Message message = Message.obtain();
        message.arg1 = 1;
        workHandler.sendMessage(message);



    }


    /**
     * 事件分发
     * @param view
     */
    public void event_dispatch(View view){
        startActivity(new Intent(MainActivity.this, EventActivity.class));
    }

    public void sendRequest(View view){
        HttpProxy.obtain().Get("https://www.baidu.com/", null, new HttpCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.d("MainActivity","onSuccess(): " + s);
            }

            @Override
            public void onFailure(String error) {
                Log.d("MainActivity","onFailure(): " + error);
            }
        });
    }

    //测试
    public void volley(View view){
        //调用网络请求方法，发送一个请求
        Volley.sendJsonRequest("", null, String.class, new IDataListener<String>() {
            @Override
            public void onSuccess(String data) {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    //内存泄漏测试
    public void memroy_test(View view){
        startActivity(new Intent(MainActivity.this,MemoryAnalazy.class));
        Handler handler;
        Message.obtain();

    }


    //RxJava
    public void rxjava(View view) {

        startActivity(new Intent(MainActivity.this, RXJavaActivity.class));
    }
}
