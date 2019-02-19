package com.example.myandroidproject.android_libray.rxjava2.new_test.map;

import com.example.myandroidproject.android_libray.rxjava2.new_test.Subscrible;

//中间人的具体实现
public class OperatorMap<T,R> implements Operator<R,T> {

    //Warning:一个是super：作为参数，一个是extends：作为返回值
    private Func1<? super T,? extends R> transform;


    public OperatorMap(Func1<? super T, ? extends R> transform) {
        this.transform = transform;
    }

    //返回事件最终的处理人对象 打杀猪刀的人
    @Override
    public Subscrible<? super T> call(Subscrible<? super R> subscrible) {
        return new MapSubscribe<>(subscrible,transform);
    }

   //打杀猪刀的人
    private class MapSubscribe<T,R> extends Subscrible<T>{

        //链子最后那个观察者，由他接收最终结果并处理
        private Subscrible<? super R> action1;
        private Func1<? super T,? extends R> transform;

        public MapSubscribe(Subscrible<? super R> action1, Func1<? super T, ? extends R> transform) {
            this.action1 = action1;
            this.transform = transform;
        }

        @Override
        public void onNext(T t) {
            //完成转换，R是最终的结果，t是第一个调用者传进来的参数
            R r = transform.call(t);
            //给最后一个调用者进行处理
            action1.onNext(r);
        }
    }
}
