package com.example.myandroidproject.performance_opt.thread_test;

import android.os.AsyncTask;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//模拟AsyncTask异步任务中出现的问题
//问题1：当任务队列是满的，并且所有任务都正在运行，在插入时会抛出异常（看源码）
//问题2：内存泄漏，当异步任务正在执行，退出Activity时，cancel()并不起作用，异步任务仍然在运行
//导致Activity内存泄漏
public class AsyncTaskTest1 {

    public static void main(String[] args){
        int CPU_COUNT = Runtime.getRuntime().availableProcessors();//可用的CPU个数
        int CORE_POOL_SIZE = CPU_COUNT + 1;
        int MAXNUM_POOL_SIZE = CPU_COUNT * 2 + 1;
        int KEEP_ALIVE = 1;

        //异步任务队列
        final BlockingQueue<Runnable> workQueue =
                new LinkedBlockingQueue<>(500);

        Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE,MAXNUM_POOL_SIZE,KEEP_ALIVE,
                TimeUnit.SECONDS,workQueue);

        //执行异步任务
        for (int i = 0; i < 200; i++){//当任务队列是满的，并且所有任务都正在运行，在插入时会抛出异常
            THREAD_POOL_EXECUTOR.execute(new MyTask());
        }
    }

    static class MyTask implements Runnable{

        @Override
        public void run() {

            //System.out.println("ThreadName" + Thread.currentThread().getName());
//            while(true){
                try {
                    System.out.println("ThreadName" + Thread.currentThread().getName());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//            }
        }
    }

    class MyTask1 extends AsyncTask<Void,Integer,String>{

        @Override
        protected String doInBackground(Void... voids) {

            //再次判断是否取消掉了，并在onDestory方法中调用cancel()方法
            while(!isCancelled()){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("异步任务正在执行");
            }

            return "hello world";
        }
    }


}




















