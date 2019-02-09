package com.example.myandroidproject.android_libray.rxjava2.imageloader;


import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class RequestCreator {
    private CacheObservable mMemoryCacheObservable;
    private CacheObservable mDiskCacheObservable;
    private CacheObservable mNetworkCacheObservable;

    public RequestCreator(Context context){
        mMemoryCacheObservable = new MemoryCacheObservable();
        mDiskCacheObservable = new DiskCacheObservable(context);
        mNetworkCacheObservable = new NetworkCacheObservable();
    }

    public Observable<Image> getImageFromMemory(String url){
        return mMemoryCacheObservable.getImage(url);
    }

    public Observable<Image> getImageFromDisk(String url){
        return mDiskCacheObservable.getImage(url).filter(new Predicate<Image>() {
            @Override
            public boolean test(Image image) throws Exception {
                return image.getBitmap() != null;
            }
        }).doOnNext(new Consumer<Image>() {
            @Override
            public void accept(Image image) throws Exception {
                mMemoryCacheObservable.putImageToCache(image);
            }
        });
    }

    public Observable<Image> getImageFromNetwork(String url){
        return mNetworkCacheObservable.getImage(url).filter(new Predicate<Image>() {
            @Override
            public boolean test(Image image) throws Exception {
                return image.getBitmap() != null;
            }
        }).doOnNext(new Consumer<Image>() {
            @Override
            public void accept(Image image) throws Exception {
                mMemoryCacheObservable.putImageToCache(image);
                mDiskCacheObservable.putImageToCache(image);
            }
        });
    }
}
