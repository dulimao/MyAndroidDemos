package com.example.myandroidproject.android_libray.rxjava2;

import com.example.myandroidproject.android_libray.rxjava2.bean.Api;
import com.example.myandroidproject.android_libray.rxjava2.bean.User;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * @author 杜立茂
 * @date 2019/1/16 20:28
 * @description scheduler:线程调度
 */
public class SchedulerTest {

    public static void main(String[] args) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://www.baidu.com/")
//                .addConverterFactory(GsonConverterFactory.create())
////                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//        mApi = retrofit.create(Api.class);


        Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(final ObservableEmitter<User> e) throws Exception {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url("http://www.baidu.com").build();
                Response call = okHttpClient.newCall(request).execute();
                System.out.println(call.body().string());

                System.out.println("currentThreadName: " + Thread.currentThread().getName());

                User user = new User("110", "杜鑫");
                e.onNext(user);
                e.onComplete();

            }
        })
                .subscribeOn(Schedulers.io())//切换子线程执行耗时任务，内部实现是线程池
                .observeOn(AndroidSchedulers.mainThread())//切换回主线程进行UI更新
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {
                        System.out.println("user: " + user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        System.out.println("e: " + e.getStackTrace());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete()");
                    }
                });


    }


}
