package com.zy.patterns.listenerMode;

/**
 * Created by ecfgikd on 2017/10/25.
 */
public class DemoListener1 implements DemoListener {
    @Override
    public void handleEvent(DemoEvent dm) {
        System.out.println("listener1 ...");
        dm.say();
    }
}
