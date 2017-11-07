package com.zy.concurrent;

import java.util.concurrent.*;

/**
 * Created by ecfgikd on 2017/9/25.
 */
public class FutureTest1 {
    static ExecutorService exec = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        Future future = exec.submit(new Task1());
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}



class Task1 implements Callable<String> {

    @Override
    public String call() throws Exception {
        throw new RuntimeException("a uncheck exception");
    }
}
