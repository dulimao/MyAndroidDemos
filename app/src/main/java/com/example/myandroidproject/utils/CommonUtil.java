package com.example.myandroidproject.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class CommonUtil {

    private static Context mContext;//测试内存泄漏

    public static CommonUtil instance;

    private CommonUtil(){

    }

    public static CommonUtil getInstance(Context context){
        synchronized (CommonUtil.class){
            if (instance == null){
                instance = new CommonUtil();
                mContext = context;
            }
        }
        return instance;
    }

    public void toast(){
        Toast.makeText(mContext,"hello wrold",Toast.LENGTH_LONG).show();
    }
}
