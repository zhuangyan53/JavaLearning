package com.zy.connectionPool;

/**
 * Created by ecfgikd on 2017/10/19.
 */
public class Connection {
    static int count = 0;
    private int id = 0;
    public Connection(){
        this.id = count++;
        System.out.println("new Connection:"+this.id+" created");
    }

    public void destroyConnection(){
        System.out.println("connection :"+this.id
                +"destroyed");
    }
}