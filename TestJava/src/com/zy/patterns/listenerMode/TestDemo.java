package com.zy.patterns.listenerMode;

/**
 * Created by ecfgikd on 2017/10/25.
 */
public class TestDemo {
    EventSource  es;
    public TestDemo(){
        try{
            es = new EventSource();
            DemoListener1 listener1 = new DemoListener1();
            es.addDemoListener(listener1);
            es.addDemoListener(new DemoListener() {
                @Override
                public void handleEvent(DemoEvent dm) {
                    System.out.println("listen in Anonymous");
                }
            });
            es.notifyDemoEvent();
        }catch(Exception e){

        }
    }

    public static void main(String[] args) {
        new TestDemo();
    }
}
