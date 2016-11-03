package com.xueyan.personal.java;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
//线程
public class Threadd {

    private static Semaphore semaphore = new Semaphore(5);
    ;

    public static void main(String[] args) {
        Executor executor1 = Executors.newSingleThreadExecutor();
        Executor executor2 = Executors.newCachedThreadPool();//缓存线程池
        Executor executor3=  Executors.newFixedThreadPool(5);//固定线程
        Executor executor4=  Executors.newScheduledThreadPool(5);//计划任务
        //创建线程队列
        LinkedBlockingDeque<Runnable> linkedBlockingDeque = new LinkedBlockingDeque<>(100);
      //创建线程工厂
        ThreadFactory threadfactory = new ThreadFactory() {
            int mInt = 0;
            AtomicInteger mAtomicInteger= new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("MyThread"+mAtomicInteger.getAndIncrement());//mInt++ 线程不安全
                return thread;
            }
        };

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 100, 1L, TimeUnit.SECONDS, linkedBlockingDeque, threadfactory);
        for (int i = 0; i < 100; i++) {
         //自定义的线程
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    method();
                }
            });


            //这是系统的线程池
            executor1.execute(new Runnable() {
                @Override
                public void run() {
                    method();
                }
            });

            //使用的是并发 Sefaphore类
            new Thread(new Runnable() {
                @Override
                public void run() {
                    method1();
                }
            }).start();
        }

    }

    private static void method() {
        System.out.println("name" + Thread.currentThread().getName() + "in");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("name" + Thread.currentThread().getName() + "out");
    }
    private static void method1() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("name" + Thread.currentThread().getName() + "in");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("name" + Thread.currentThread().getName() + "out");

        semaphore.release();
    }

}
