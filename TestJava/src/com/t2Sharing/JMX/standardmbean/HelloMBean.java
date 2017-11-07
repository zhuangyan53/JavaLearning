package com.t2Sharing.JMX.standardmbean;

/**
 * Created by ecfgikd on 2017/11/7.
 */
public interface HelloMBean {
    public void sayHello();
    public int add(int x, int y);
    public String getName();
    public int getCacheSize();
    public void setCacheSize(int size);
}
