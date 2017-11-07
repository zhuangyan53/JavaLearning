package com.zy.patterns.Observe;

import java.util.Observable;

/**
 * Created by ecfgikd on 2017/10/25.
 */
public class BeingWatched extends Observable {
    void counter(int period){
        for(; period>=0; period--){
            setChanged();
            notifyObservers(new Integer(period));
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                System.out.println("Sleep interuped!");
            }
        }
    }
}
