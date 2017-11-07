package com.zy.concurrent;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Created by ecfgikd on 2017/9/13.
 */
public class ExecutorTest {
    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        ArrayList <Future<String>> al = new ArrayList<Future<String>>();
        for (int i=0;i<5;i++) {
            al.add(es.submit(new TaskCall()));
        }
        es.shutdown();
    }
}

class TaskCall implements Callable{
    static int count = 0;
    private int id = 0;
    public TaskCall(){
        id = count++;
    }

    @Override
    public String call() throws Exception {
        System.out.println(id+" start.");
        TimeUnit.MILLISECONDS.sleep(300);
        if(id==3) throw new RuntimeException("runtime exception");
        System.out.println(id+" finished");
        return String.valueOf(id);
    }
}
