package com.zy.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ecfgikd on 2017/6/28.
 */
public class LiftOff implements  Runnable {
    protected int countdown=10;
    private int id;
    private static CountDownLatch latch = new CountDownLatch(1);
    public LiftOff(int id){
        this.id=id;
    }
    @Override
    public void run() {
//        try {
////            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        while (countdown-- >0){
            System.out.println(id+"is :"+countdown);
            if(countdown>5) Thread.yield();
        }
    }

    public static void main(String args[]){
//        LiftOff lo = new LiftOff(1);
//        lo.run();
//        for(int i = 1;i<5;i++){
//            new Thread(new LiftOff(i)).start();
//        }
//        latch.countDown();
//        ExecutorService exec =Executors.newCachedThreadPool();
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 1;i<5;i++)
        exec.submit(new Taskwithresult(i));
    }
}

class Taskwithresult implements Callable<String>{
    private int id;
    public Taskwithresult(int id){
        this.id=id;
    }
    @Override
    public String call() throws Exception {
        return "result of id:"+id;
    }
}
