package com.zy.concurrent;

/**
 * Created by ecfgikd on 2017/9/13.
 */
public class TestSync2 implements Runnable{
    int b= 100;
    synchronized void m1() throws InterruptedException {

        b=1000;
        Thread.sleep(5000);
        System.out.print("b="+b);
    }
    synchronized void m2() throws InterruptedException {

        Thread.sleep(2500);
        b=2000;
    }

    public static void main(String[] args) throws InterruptedException {
        TestSync2 tt = new TestSync2();
        Thread t = new Thread(tt);
        t.start();

        tt.m2();
        System.out.println("main "+tt.b);

    }
    public void run(){
        try {
            m1();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
