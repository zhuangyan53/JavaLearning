package com.zy.concurrent;

/**
 * Created by ecfgikd on 2017/9/13.
 */
public class JoinTest implements Runnable{
    long sleeptime;
    int id;
    Thread other;
    public JoinTest(long time, int id, Thread thread){
        this.sleeptime=time;
        this.id=id;
        this.other=thread;
    }
    @Override
    public void run() {
        try {
            other.start();
            other.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(id+"sleeping:"+this.sleeptime);
            Thread.sleep(sleeptime);
            System.out.println(id+"wakeup:"+this.sleeptime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread other = new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println("other sleeping");
                    Thread.sleep(2000L);
                    System.out.println("other wakeup");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t1 = new Thread(new JoinTest(1000L, 1, other));
        t1.start();
        System.out.println("main exit");
    }
}
