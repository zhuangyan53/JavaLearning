package com.zy.concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ecfgikd on 2017/9/19.
 */
public class Exercise1 implements Runnable {
    private Integer id;
    public Exercise1(){
        id = new Random().nextInt(32);
//        System.out.println(id+":newed");
    }
    @Override
    public void run() {
        System.out.println("task"+id+":started");
        System.out.println(Thread.currentThread().getId());
        Thread.yield();
        Thread.yield();
        Thread.yield();
        System.out.println("task"+id+":closed");
    }

    public static void main(String[] args) {
//        ExecutorService exec = Executors.newCachedThreadPool();

//        ExecutorService exec = Executors.newSingleThreadExecutor();
        ExecutorService exec = Executors.newFixedThreadPool(2);
        for(int i=0;i<20;i++){
            exec.execute(new Exercise1());
        }
        exec.shutdown();
    }
}
