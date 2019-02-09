package com.example.myandroidproject.android_libray.rxjava2;

import android.util.Log;

import com.example.myandroidproject.android_libray.rxjava2.bean.User;
import com.example.myandroidproject.android_libray.rxjava2.bean.UserParams;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class Main {

    private static final String TAG = "Main";
    public static void main(String[] args) {

//        Observable<String> observable = getObservable();
//        Observer<String> observer = getObserver();
//        observable.subscribe(observer);


//        mapTest();
//        flatMapTest("dlm", "123456");
//        debounceTest();
//        mergeTest();
//        countDonwTest();

        loadImage();
    }

    public static Observable<String> getObservable() {
//        return Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                e.onNext("大保健");
//                e.onNext("泡吧");
//                e.onComplete();
//            }
//        });

//        return Observable.just("hello world","I am a Android developer");

        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "hello world";
            }
        });
    }

    public static Observer<String> getObserver() {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe()");
            }

            @Override
            public void onNext(String value) {
                System.out.println("onNext() : " + value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError()");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete()");
            }
        };
        return observer;
    }


    //变换操作符
    public static void mapTest() {
        Observable.just(110).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                // do something
                return integer + "  hello world";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("变换之后的内容 " + s);
            }
        });
    }


    //先得到用户ID，在根据ID获取用户信息,适合链式的网络请求操作
    public static void flatMapTest(final String userName, final String password) {
        UserParams userParams = new UserParams(userName, password);
        Observable.just(userParams).flatMap(new Function<UserParams, ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> apply(UserParams params) throws Exception {

                //模拟网络请求，通过用户名和密码进行登录，然后得到用户ID
                System.out.println("用户开始登录： 用户名： " + params.getUserName() + "  用户密码： " + params.getUserPassword());
                Thread.sleep(1000);

                return Observable.just(110);
            }
        }).switchMap(new Function<Integer, ObservableSource<User>>() {//switchMap清空上次的网络请求，进行本次网络请求
            @Override
            public ObservableSource<User> apply(Integer userId) throws Exception {
                System.out.println("用户ID：" + userId);
                return Observable.just(new User(userId + "", "dlm"));
            }
        }).subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                System.out.println("用户信息：" + user);
            }
        });
    }

    //防抖动操作符
    public static void debounceTest() {
        Observable.just(1, 2, 3, 4, 5, 6).debounce(500,TimeUnit.MILLISECONDS).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println(integer);
                    }
                });
    }

    //合并操作符
    public static void mergeTest(){
        Observable.merge(Observable.just(1,3,5),Observable.just("2","4","6")).subscribe(new Observer<Serializable>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Serializable value) {
                System.out.println("onComplete() " + value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError()");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete()");
            }
        });
    }

    public static void countDonwTest(){
        Observable.interval(1,TimeUnit.SECONDS)
                .take(10)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return 10 - aLong;
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        System.out.println("doOnSubscribe");
                    }
                })
               .subscribe(new Observer<Long>() {
                   @Override
                   public void onSubscribe(Disposable d) {

                   }

                   @Override
                   public void onNext(Long value) {
                        System.out.println(value);
                   }

                   @Override
                   public void onError(Throwable e) {

                   }

                   @Override
                   public void onComplete() {
                        System.out.println("onComplete()");
                   }
               });
    }


    //图片三级缓存
    public static void loadImage(){
        Observable<String> memoryObser = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("memory_cache");
                e.onComplete();
            }
        });

        Observable<String> diskObser = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("disk_cache");
                e.onComplete();
            }
        });

        Observable<String> networkObser = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("network");
                e.onComplete();
            }
        });


        Observable.concat(memoryObser,diskObser,networkObser).filter(new Predicate<String>() {
            @Override
            public boolean test(String s) throws Exception {
                return s != null;
            }
        }).firstElement().toObservable().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
              System.out.println("value: " + value);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });

    }


}
