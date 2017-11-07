package com.zy.concurrent;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by ecfgikd on 2017/9/19.
 */
public class AtomicTest {
    static AtomicBoolean flag = new AtomicBoolean(true);

    public static void main(String[] args) {
        flag.compareAndSet(true,false);
        System.out.println(flag.get());
    }
}
