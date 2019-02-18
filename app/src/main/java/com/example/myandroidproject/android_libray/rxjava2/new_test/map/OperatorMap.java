package com.example.myandroidproject.android_libray.rxjava2.new_test.map;

import com.example.myandroidproject.android_libray.rxjava2.new_test.Subscrible;

public class OperatorMap<T,R> implements Operator<R,T> {

    //Warning:一个是super：作为参数，一个是extends：作为返回值
    private Func1<? super T,? extends R> transform;


    public OperatorMap(Func1<? super T, ? extends R> transform) {
        this.transform = transform;
    }

    @Override
    public Subscrible<? super T> call(Subscrible<? super R> subscrible) {
        return new MapSubscribe<>(subscrible,transform);
    }


    private class MapSubscribe<T,R> extends Subscrible<T>{

        private Subscrible<? super R> action1;
        private Func1<? super T,? extends R> transform;

        public MapSubscribe(Subscrible<? super R> action1, Func1<? super T, ? extends R> transform) {
            this.action1 = action1;
            this.transform = transform;
        }

        @Override
        public void onNext(T t) {
            //完成转换
            R r = transform.call(t);
            action1.onNext(r);
        }
    }
}
