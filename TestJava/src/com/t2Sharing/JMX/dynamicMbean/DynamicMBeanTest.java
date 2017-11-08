package com.t2Sharing.JMX.dynamicMbean;

import com.t2Sharing.JMX.mbeanServer.JmxServer;
import com.t2Sharing.JMX.util.Util;

import javax.management.*;
import java.io.IOException;

/**
 * Created by ecfgikd on 2017/11/8.
 */
public class DynamicMBeanTest {

    public static void main(String[] args) throws IOException, MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, InterruptedException {
        MBeanServer mbs = JmxServer.getExposedMbeanServer();

        ObjectName on = new ObjectName("com.t2Sharing.JMX.dynamicMbean:type=HelloDynamic");
        mbs.registerMBean(new HelloDynamciMBean(), on);

        Util.sleepUntilReciveExitFromKeyBoard();
        JmxServer.stopServer();
    }
}
