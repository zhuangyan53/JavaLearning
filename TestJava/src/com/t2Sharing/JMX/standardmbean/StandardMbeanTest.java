package com.t2Sharing.JMX.standardmbean;

import com.t2Sharing.JMX.mbeanServer.JmxServer;
import com.t2Sharing.JMX.util.Util;

import javax.management.*;
import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by ecfgikd on 2017/11/7.
 */
public class StandardMbeanTest {


    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, IOException, InterruptedException {
        MBeanServer mbs = JmxServer.getMbeanServer();
        HelloMBean hello = new Hello();
        ObjectName on = new ObjectName("com.t2Sharing.JMX.standardmbean:type=Hello");
        mbs.registerMBean(hello, on);

        Util.sleepUntilReciveExitFromKeyBoard();
    }
}
