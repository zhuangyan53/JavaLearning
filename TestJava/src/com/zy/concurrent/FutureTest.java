package com.zy.concurrent;

import java.util.concurrent.*;

/**
 * Created by ecfgikd on 2017/9/19.
 */
public class FutureTest {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        task1 task = new task1(true);
        Future<String> f= exec.submit(task);
        TimeUnit.SECONDS.sleep(3);
        System.out.println("cancel.........");
        f.cancel(true);
        System.out.println("cancel.........stop");
        TimeUnit.SECONDS.sleep(3);
        task.setFlag(false);
        exec.shutdownNow();

    }
}
class task1 implements Callable{
public task1(Boolean flag){
    this.flag=flag;
}
public void setFlag(Boolean flag){
    this.flag=flag;
}
    Boolean flag = true;
    @Override
    public String call() throws Exception {
        while(flag){
            System.out.println("running");
        }
        System.out.println("flag is false");
        return "closed";
    }
}

