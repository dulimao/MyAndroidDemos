package com.example.myandroidproject.android_libray.rxjava2.imageloader;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class CacheObservable {

    public Observable<Image> getImage(final String url){

        Observable<Image> observable = Observable.create(new ObservableOnSubscribe<Image>() {
            @Override
            public void subscribe(ObservableEmitter<Image> e) throws Exception {
                e.onNext(getImageFromCache(url));
                e.onComplete();
            }
        });
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public abstract Image getImageFromCache(String url);
    public abstract void putImageToCache(Image image);

}
