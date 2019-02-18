package com.example.myandroidproject.android_libray.rxjava2.new_test.map;


import com.example.myandroidproject.android_libray.rxjava2.new_test.OnSubscribe;
import com.example.myandroidproject.android_libray.rxjava2.new_test.Subscrible;

/**
 * 也是一个观察者，相当于中间人
 * @param <T>
 * @param <R>
 */
public class OnSubscribeLift<T,R> implements OnSubscribe<R> {


    //观察者的引用
    OnSubscribe<T> parent;

    Operator<? extends R,? super T> operator;

    public OnSubscribeLift(OnSubscribe<T> parent, Operator<? extends R, ? super T> operator) {
        this.parent = parent;
        this.operator = operator;
    }

    @Override
    public void call(Subscrible<? super R> subscrible) {

        Subscrible<? super T> result = operator.call(subscrible);
        parent.call(result);
    }
}
