package com.t2Sharing.JMX.notificationMbean;

import javax.management.Notification;
import javax.management.NotificationListener;

/**
 * Created by ecfgikd on 2017/11/8.
 */
public class Listener1 implements NotificationListener {


    @Override
    public void handleNotification(Notification notification, Object handback) {
        System.out.println("Listener1 recived notification" + notification.getTimeStamp()+notification.getType()+notification.getMessage()+notification.getSource());

        if(handback!=null){

        }
    }
}
