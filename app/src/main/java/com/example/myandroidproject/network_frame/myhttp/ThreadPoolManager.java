package com.example.myandroidproject.network_frame.myhttp;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
*@author 杜立茂
*@date 2018/12/28 9:32
*@description 线程池管理单例类  架构图见：架构图.png
*/
public class ThreadPoolManager {

    //1、把任务放到请求队列中
    private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue();

    public void execute(Runnable runnable){
        if (runnable == null)
            return;
        try {
            queue.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //2、把队列中的任务放到线程池中
    private ThreadPoolExecutor threadPoolExecutor;
    private ThreadPoolManager(){
        threadPoolExecutor = new ThreadPoolExecutor(4,
                20,
                15, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(100),
                rejectedExecutionHandler);

        threadPoolExecutor.execute(runnable);

    }

    //拒绝策略，当一个任务由于某种原因没有得到处理，但是已经超过存活时间，就让他重新回到队列，等待下次执行
    private RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                queue.put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    };


    //3、让他们工作起来
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true){
                Runnable run = null;
                try {
                    //不停的从队列里取，当没有的时候会阻塞等待
                    run = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (run != null){
                    threadPoolExecutor.execute(run);
                }
            }
        }
    };

    private static ThreadPoolManager ourInstance = new ThreadPoolManager();
    public static ThreadPoolManager getInstance(){
        return ourInstance;
    }
}
