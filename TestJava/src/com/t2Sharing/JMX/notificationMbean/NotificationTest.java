package com.t2Sharing.JMX.notificationMbean;

import com.t2Sharing.JMX.mbeanServer.JmxServer;
import com.t2Sharing.JMX.util.Util;

import javax.management.*;
import java.io.IOException;

/**
 * Created by ecfgikd on 2017/11/8.
 */
public class NotificationTest {

    public static void main(String[] args) throws IOException, MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, InterruptedException {
        MBeanServer mbs = JmxServer.getExposedMbeanServer();
        Hello hello = new Hello();
        mbs.registerMBean(hello, new ObjectName("com.t2Sharing.JMX.notificationMbean:type=HelloNotification"));

        hello.addNotificationListener(new Listener1(), null, null);


        Util.sleepUntilReciveExitFromKeyBoard();
        JmxServer.stopServer();

    }
}
