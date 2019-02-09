package com.example.myandroidproject.network_frame.myhttp;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class JsonHttpListener<T> implements IHttpListener{

    Class<?> responseClass;//响应结果的字节码对象
    private IDataListener<T> dataListener;

    //线程切换
    private Handler handler = new Handler(Looper.getMainLooper());

    public JsonHttpListener(Class responseClass,IDataListener dataListener){
        this.responseClass = responseClass;
        this.dataListener = dataListener;
    }


    @Override
    public void onSuccess(InputStream inputStream) {
        Gson gson = new Gson();
        String responseContent = getContent(inputStream);
//        responseClass = analysisClassInfo(this);//方式二
        final T result = (T) gson.fromJson(responseContent,responseClass);
        //把结果传回调用层
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (dataListener != null){
                    dataListener.onSuccess(result);
                }
            }
        });

    }

    @Override
    public void onFailure() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (dataListener != null){
                    dataListener.onFailure();
                }
            }
        });
    }

    /**
     * 字节流转换成字符串
     * @param inputStream
     * @return
     */
    private String getContent(InputStream inputStream){
        String content = null;
        BufferedReader reader = null;
        try{
             reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb  = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }
            return sb.toString();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

    private Class<?> analysisClassInfo(Object object){
        Type type = object.getClass().getGenericSuperclass();
        Type[] paramsTypes = ((ParameterizedType) type).getActualTypeArguments();
        return (Class<?>) paramsTypes[0];
    }
}
