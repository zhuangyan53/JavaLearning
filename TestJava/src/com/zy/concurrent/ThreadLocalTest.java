package com.zy.concurrent;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.*;

/**
 * Created by ecfgikd on 2017/9/15.
 */
public class ThreadLocalTest {

    public static ThreadLocal <Integer> value = new ThreadLocal<Integer>(){

        private Random rand = new Random(47);
        protected synchronized Integer initialValue(){
            return rand.nextInt(10000);
        }
    };
    public static void increment(){
        value.set(value.get()+1);
    }

    public static int get(){return value.get();}

    public static void main(String [] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i =0;i<5;i++) exec.execute(new Accessor(i));
        Accessor a = new Accessor(1);
        a.run();
        TimeUnit.SECONDS.sleep(3);
        exec.shutdown();

    }
}

class Accessor implements Runnable{
    private final int id;
    public Accessor(int idn){this.id=idn;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            ThreadLocalTest.increment();
            System.out.println(this);
            Thread.yield();
        }
    }
    public String toString(){
        return "#" + id +":"+ ThreadLocalTest.get();
    }
}