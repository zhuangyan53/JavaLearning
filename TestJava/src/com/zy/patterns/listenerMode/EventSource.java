package com.zy.patterns.listenerMode;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Created by ecfgikd on 2017/10/25.
 */
public class EventSource {
    private Vector repostory = new Vector();
    public EventSource(){}
    public void addDemoListener(DemoListener d1){
        repostory.addElement(d1);
    }
    public void notifyDemoEvent(){
        Enumeration enum1 = repostory.elements();
        while(enum1.hasMoreElements()) {
            DemoListener d1 = (DemoListener) enum1.nextElement();
            d1.handleEvent(new DemoEvent(this));
        }
    }
}
