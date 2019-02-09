package com.example.myandroidproject.android_libray.rxjava2.imageloader;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
*@author 杜立茂
*@date 2019/1/17 21:09
*@description 内存缓存
*/
public class MemoryCacheObservable extends CacheObservable {

    int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    int cacheSize = maxMemory / 8;
    private LruCache<String, Bitmap> mCache = new LruCache<String,Bitmap>(cacheSize){
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight() / 1024;
        }
    };

    @Override
    public Image getImageFromCache(String url) {
        Log.d("MemoryCacheObservable","从内存缓存中获取图片");
        Bitmap bitmap = mCache.get(url);
         return new Image(url,bitmap);

    }

    @Override
    public void putImageToCache(Image image) {
        Log.d("MemoryCacheObservable","放入内存缓存中");
        mCache.put(image.getUrl(),image.getBitmap());
    }
}
