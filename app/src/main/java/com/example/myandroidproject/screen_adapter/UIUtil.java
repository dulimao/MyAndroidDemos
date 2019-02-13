package com.example.myandroidproject.screen_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;

public class UIUtil {

    private static final String DIME_CLASS_NAME = "com.android.internal.R$dimen";
    private Context mContext;
    //标准值，正常情况下应该保存在配置文件中
    private static final int STANDARN_WIDTH = 1080;
    private static final int STANDARN_HEIGHT = 1920;
    //实际设备值
    private  float displayMetricsWidth;
    private  float displayMetricsHeight;

    private int systemStatusBarHeight;


    //单例
    @SuppressLint("StaticFieldLeak")
    private static UIUtil instance;
    public  static UIUtil getInstance(Context context){
        if (instance == null){
            instance = new UIUtil(context);
        }
        return instance;
    }
    private UIUtil(Context context){
        this.mContext = context;
        //初始化设备相关信息
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        if (this.displayMetricsWidth == 0.0f && this.displayMetricsHeight == 0.0f){
            manager.getDefaultDisplay().getMetrics(metrics);
            //得到状态栏高度
            systemStatusBarHeight = getStatusBarHeight(context);
            if (metrics.widthPixels > metrics.heightPixels){
                //横屏
                displayMetricsWidth = (float) metrics.heightPixels;
                displayMetricsHeight = (float) (metrics.widthPixels - systemStatusBarHeight);
            }else {
                //竖屏
                displayMetricsWidth = (float) metrics.widthPixels;
                displayMetricsHeight = (float) (metrics.heightPixels - systemStatusBarHeight);
            }
        }

    }

    @SuppressLint("PrivateApi")
    private int getStatusBarHeight(Context context){
        try {
            Class clazz = Class.forName(DIME_CLASS_NAME);
            Object obj = clazz.newInstance();
            Field field = clazz.getDeclaredField("system_bar_height");
            int id = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public float getHorizontalScaleRatio(){
        return displayMetricsWidth / (float) STANDARN_WIDTH;
    }

    public float getVerticalScaleRatio(){
        return displayMetricsHeight / (float) (STANDARN_HEIGHT - systemStatusBarHeight);
    }









}
