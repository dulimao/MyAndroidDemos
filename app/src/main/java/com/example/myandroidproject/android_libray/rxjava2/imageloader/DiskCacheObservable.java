package com.example.myandroidproject.android_libray.rxjava2.imageloader;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;

/**
*@author 杜立茂
*@date 2019/1/17 21:09
*@description 磁盘缓存
*/
public class DiskCacheObservable extends CacheObservable {

    private DiskLruCache mDiskLruCache;
    private int maxSize = 20 * 1024 * 1024;
    private Context mContext;

    public DiskCacheObservable(Context context){
        this.mContext = context;
        initDiskCache();
    }

    @Override
    public Image getImageFromCache(String url) {
        Log.d("DiskCacheObservable","从磁盘缓存中获取图片");
        Bitmap bitmap = getBitmapFromCache(url);
        return new Image(url,bitmap);
    }

    @Override
    public void putImageToCache(final Image image) {
        Log.d("DiskCacheObservable","放入磁盘缓存中");
        Observable.create(new ObservableOnSubscribe<Image>() {
            @Override
            public void subscribe(ObservableEmitter<Image> e) throws Exception {
                putBitmapToCache(image);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    private void initDiskCache(){
        File file = Util.getDiskCacheDir(mContext,"imageload_cache");
        if (!file.exists()){
            file.mkdir();
        }
        try {
            mDiskLruCache = DiskLruCache.open(file,1,1,maxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getBitmapFromCache(String url){

        FileInputStream fileInputStream = null;
        FileDescriptor fileDescriptor = null;
        try {

            String key = Util.getMD5String(url);
            DiskLruCache.Snapshot value = mDiskLruCache.get(key);
            if (value != null){
                fileInputStream = (FileInputStream) value.getInputStream(0);
                fileDescriptor = fileInputStream.getFD();
            }
            Bitmap bitmap = null;
            if (fileDescriptor != null){
                bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fileDescriptor != null && fileInputStream!= null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    private void putBitmapToCache(Image image){
        try {
            String key = Util.getMD5String(image.getUrl());
            DiskLruCache.Editor editor =mDiskLruCache.edit(key);
            if (editor != null){
                OutputStream outputStream = editor.newOutputStream(0);
                //下载图片缓存到DiskLruCache中
                boolean isSuccess = downLoad(image.getUrl(),outputStream);
                if (isSuccess){
                    editor.commit();
                }else {
                    editor.abort();
                }

                mDiskLruCache.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean downLoad(String url,OutputStream outputStream){
        HttpURLConnection conn = null;
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            URL netUrl = new URL(url);
            conn = (HttpURLConnection) netUrl.openConnection();
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200){
                in = new BufferedInputStream(conn.getInputStream(),8 * 1024);
                out = new BufferedOutputStream(outputStream,8 * 1024);
                int b = 0;
                while ((b = in.read()) != -1){
                    out.write(b);
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (conn != null){
                conn.disconnect();
            }
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
