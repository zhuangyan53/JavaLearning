package com.t2Sharing.JMX.mbeanServer;

import javax.management.MBeanServer;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.rmi.RMIConnectorServer;
import javax.management.remote.rmi.RMIJRMPServerImpl;
import javax.management.remote.rmi.RMIServerImpl;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;

/**
 * Created by ecfgikd on 2017/11/7.
 */
public class JmxServer {
    private static MBeanServer mbs = null;

    static public synchronized MBeanServer getMbeanServer(){
        if(mbs == null){
            mbs = ManagementFactory.getPlatformMBeanServer();
        }

        return mbs;
    }

    static public synchronized void exposeMbeanServer() throws IOException {
        String serviceUrl = "service:jmx:rmi://"+hostName+":"+jmxPort+"/jndi/rmi://"+hostName+":"+rmiRegistryPort+"/connector";
        JMXServiceURL url = new JMXServiceURL(serviceUrl);
        new RMIConnectorServer(url, null, null, getMbeanServer());
    }
}
