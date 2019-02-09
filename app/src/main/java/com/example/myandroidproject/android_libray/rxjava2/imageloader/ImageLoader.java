package com.example.myandroidproject.android_libray.rxjava2.imageloader;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

public class ImageLoader {

    private String mUrl;

    private static ImageLoader instance;

    private RequestCreator mRequestCreator;

    private ImageLoader(Builder builder) {
        mRequestCreator = new RequestCreator(builder.context);
    }

    public static ImageLoader width(Context context) {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                instance = new Builder(context).build();
            }
        }
        return instance;
    }

    public ImageLoader load(String url) {
        mUrl = url;
        return instance;
    }

    public void into(final ImageView imageView) {

        Observable.concat(mRequestCreator.getImageFromMemory(mUrl)
                , mRequestCreator.getImageFromDisk(mUrl)
                , mRequestCreator.getImageFromNetwork(mUrl)).filter(new Predicate<Image>() {
            @Override
            public boolean test(Image image) throws Exception {
                return image.getBitmap() != null;
            }
        }).firstElement().toObservable().subscribe(new Observer<Image>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Image value) {
                imageView.setImageBitmap(value.getBitmap());
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.d("ImageLoader","onComplete()");
            }
        });
    }


    static class Builder {

        private Context context;
        public Builder(Context context){
            this.context = context;
        }

        public ImageLoader build() {
            return new ImageLoader(this);
        }
    }

}
