package com.example.myandroidproject.android_libray.rxjava2.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
*@author 杜立茂
*@date 2019/1/17 20:55
*@description 从网络下载图片
*/
public class NetworkCacheObservable extends CacheObservable {


    @Override
    public Image getImageFromCache(String url) {
        Log.d("NetworkCacheObservable","从网络下载图片");
        Bitmap bitmap = downLoadImage(url);
        Image image = new Image(url,bitmap);
        return image;
    }

    @Override
    public void putImageToCache(Image image) {

    }

    private Bitmap downLoadImage(String url){
        Bitmap bitmap = null;
        try {
            URL netUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) netUrl.openConnection();
            conn.setRequestMethod("GET");
            InputStream inputStream = conn.getInputStream();
            if (conn.getResponseCode() == 200){
                bitmap = BitmapFactory.decodeStream(inputStream);
            }
            if (inputStream != null){
                inputStream.close();
            }
            conn.disconnect();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
