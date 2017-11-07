package com.zy.patterns.listenerMode;

import java.util.EventListener;

/**
 * Created by ecfgikd on 2017/10/25.
 */
public interface DemoListener extends EventListener {
    public void handleEvent(DemoEvent dm);
}
