package com.zy.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by ecfgikd on 2017/9/18.
 */
class Car{
    private boolean waxOn=false;
    public synchronized void buffed(){
        waxOn=false;
        notifyAll();
    }
    public synchronized void waxed(){
        waxOn=true;
        notifyAll();
    }
    public synchronized void waitingForWaxing() throws InterruptedException {
        while (waxOn == false){
            wait();
        }
    }
    public synchronized void waitForBuffering() throws InterruptedException {
        while (waxOn=true){
            wait();
        }
    }
}
class WaxOn implements Runnable {
    private Car car;
    public WaxOn(Car c){this.car=c;}
    public void run(){
        try{
            while (!Thread.interrupted()){
                System.out.println("Wax On!");
                TimeUnit.MILLISECONDS.sleep(200);
                car.waxed();
                car.waitForBuffering();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Ending WaxOn Task");
    }

}
class WaxOff implements Runnable{
    private Car car;
    public WaxOff(Car c){this.car=c;}

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                car.waitingForWaxing();
                System.out.println("WaxOff!");
                TimeUnit.MILLISECONDS.sleep(200);
                car.buffed();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Ending WaxOff task");
    }
}

public class TestNotify {
    public static void main(String[] args) throws InterruptedException {
        Car car = new Car();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new WaxOff(car));
        exec.execute(new WaxOn(car));
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }
}
