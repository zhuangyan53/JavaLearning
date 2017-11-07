package com.zy.patterns.listenerMode;

/**
 * Created by ecfgikd on 2017/10/25.
 */
public class DemoEvent extends  java.util.EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public DemoEvent(Object source) {
        super(source);
    }
    public void say(){
        System.out.println("saying...");
    }
}
