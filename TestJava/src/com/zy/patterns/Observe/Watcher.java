package com.zy.patterns.Observe;

import java.util.Observable;

/**
 * Created by ecfgikd on 2017/10/25.
 */
public class Watcher implements  java.util.Observer {
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("update called");
    }
}


