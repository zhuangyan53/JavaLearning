package com.t2Sharing.JMX.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * Created by ecfgikd on 2017/11/7.
 */
public class Util {

    public static void sleepUntilReciveExitFromKeyBoard() throws IOException, InterruptedException {

        System.out.println("Input exit if want to stop:");
        Boolean sleepFlag = true;
        while(sleepFlag){
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            if(reader.readLine().equals("exit")){
                sleepFlag = false;
            }
            TimeUnit.SECONDS.sleep(1);

        }
    }
}
