package com.zy.concurrent;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by ecfgikd on 2017/9/25.
 */
public class CompletionServiceTest {
        static ExecutorService exec = Executors.newCachedThreadPool();

    public static void main(String[] args) {

        CompletionService <String> completionService = new ExecutorCompletionService(exec);
        for(int i=0;i<10;i++)
            completionService.submit(new TaskSleepRandom(i));
        for(int i=0;i<10;i++){
            try {
                Future<String> future = completionService.take();
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        exec.shutdown();
    }
}

class TaskSleepRandom implements Callable<String>{
    private int id;

    TaskSleepRandom(int id){
        this.id = id;
    }
    @Override
    public String call() throws Exception {

        Integer sec = new Random().nextInt(10);
        System.out.println("id:"+id+": sleeping "+sec.toString()+" seconds");
        TimeUnit.SECONDS.sleep(sec);
        return "id:"+id+": wakeuped after "+sec.toString();

    }
}
