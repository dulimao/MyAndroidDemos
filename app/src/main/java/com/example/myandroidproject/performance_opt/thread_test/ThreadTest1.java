package com.example.myandroidproject.performance_opt.thread_test;


//简单生产者消费者模型
public class ThreadTest1 {


    public static void main(String[] args){
        Object lock = new Object();//对象锁
        new Producter(lock).start();
        new Consumer(lock).start();
    }


    //产品
    static class Apple{
        /**
         * 如果不加volatile关键字，则会出现下面这种情况，并且程序停止运行
         * 生产者生产苹果：null
         * 消费者消费苹果
         * 生产者生产苹果：null
         * 消费者消费苹果
         * 生产者生产苹果：null
         * 生产者生产苹果：杜家湾大富士苹果,生产日期：1550566829223
         *
         * 但是还会出现一个问题，就是生产者和消费者需要主动的不停的判断是否有产品，
         * 陷入死循环，所以需要加上互斥锁（一个线程持有锁，其他线程就不能持有）和等待唤醒机制
         */
        //线程操作变量可见
        public volatile static String type;
    }

    static class Producter extends Thread{

        private Object lock;

        public Producter(Object lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            super.run();
            while(true){
                synchronized (lock){//互斥锁，当前线程持有，其他线程就不能持有
                    if (Apple.type != null){
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Apple.type = "杜家湾大富士苹果,生产日期：" + System.currentTimeMillis();
                    System.out.println("生产者生产苹果：" + Apple.type);
                    lock.notify();//通知消费者，现在有苹果可以购买了

                }
            }
        }
    }

    static class Consumer extends Thread{
        private Object lock;

        public Consumer(Object lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            super.run();
            while(true){
                synchronized (lock){
                    if (Apple.type == null){
                        try {
                            lock.wait();//没有苹果可消费，进入等待状态
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Apple.type = null;
                    System.out.println("消费者消费苹果");
                    lock.notify();//通知生产者继续生产
                }

            }
        }
    }
}
