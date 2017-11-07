package com.zy.patterns.Observe;

/**
 * Created by ecfgikd on 2017/10/25.
 */
public class ObserverrDemo {
    public static void main(String[] args) {
        BeingWatched beingWatched = new BeingWatched();
        Watcher watcher = new Watcher();
        beingWatched.addObserver(watcher);
        beingWatched.counter(10);
    }
}
