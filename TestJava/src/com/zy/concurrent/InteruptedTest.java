package com.zy.concurrent;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by ecfgikd on 2017/9/26.
 */
public class InteruptedTest {
    public static void main(String[] args) {
        ProducerTest1 t1 = new ProducerTest1();
        t1.start();
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.cancel();
        try {
            t1.join();
        } catch (InterruptedException e) {
            System.out.println("join interupted");
        }
        System.out.println(t1.count);
    }
}
class ProducerTest1 extends  Thread{

    public Integer count = 0;
    public void run(){

        while (!Thread.currentThread().isInterrupted()){
            count ++;
            BigInteger int1= BigInteger.valueOf(1);
            for (int i=0;i<23000;i++){
                int1 = int1.add(BigInteger.valueOf(i));
            }
        }


        System.out.println("thread interupted");
    }
    public void cancel (){interrupt();}
}
