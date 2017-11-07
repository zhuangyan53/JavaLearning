package com.zy.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by ecfgikd on 2017/10/12.
 */
public class BlockingQueueTest {
    public static void main(String[] args) {
        BlockingQueue queue = new LinkedBlockingQueue();
        try {
            queue.put("s");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(queue.poll(1000, TimeUnit.MILLISECONDS));;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            queue.poll(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }
}
