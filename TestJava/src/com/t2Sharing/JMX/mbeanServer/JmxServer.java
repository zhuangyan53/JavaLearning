package com.t2Sharing.JMX.mbeanServer;

import javax.management.MBeanServer;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.rmi.RMIConnectorServer;
import javax.management.remote.rmi.RMIJRMPServerImpl;
import javax.management.remote.rmi.RMIServerImpl;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by ecfgikd on 2017/11/7.
 */
public class JmxServer {
    private static String hostName = "127.0.0.1";
    private static int jmxPort = 9999;
    private static int rmiRegistryPort = 9998;
    private static MBeanServer mbs = null;
    private static JMXConnectorServer rmiserver;

    static public synchronized MBeanServer getExposedMbeanServer() throws IOException {
        if(mbs == null){
            mbs = ManagementFactory.getPlatformMBeanServer();
            exposeMbeanServer(mbs);
        }

        return mbs;
    }

    static private void exposeMbeanServer(MBeanServer mbs) throws IOException {
        LocateRegistry.createRegistry(rmiRegistryPort);
        String serviceUrl = "service:jmx:rmi://"+hostName+":"+jmxPort+"/jndi/rmi://"+hostName+":"+rmiRegistryPort+"/connector";
        JMXServiceURL url = new JMXServiceURL(serviceUrl);
        rmiserver = JMXConnectorServerFactory.newJMXConnectorServer(url,null,mbs);
        rmiserver.start();
        System.out.println("MBeanServer published on url:"+ serviceUrl);
    }

     public static void stopServer() throws IOException {
         if(rmiserver!=null){
             rmiserver.stop();
         }


    }
}
