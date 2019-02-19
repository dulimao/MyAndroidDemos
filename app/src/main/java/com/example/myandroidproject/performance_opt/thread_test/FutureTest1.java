package com.example.myandroidproject.performance_opt.thread_test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTest1 {

    public static void main(String[] args){
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new Task()){
            @Override
            protected void done() {
                //线程执行完成之后的回调函数
                super.done();
            }
        };
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(futureTask);
        try {
            //获取线程返回的值，阻塞等待线程执行完毕
            System.out.println(futureTask.get());

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    static class Task implements Callable<Integer>{

        @Override
        public Integer call() throws Exception {
            return 666;
        }
    }

}
