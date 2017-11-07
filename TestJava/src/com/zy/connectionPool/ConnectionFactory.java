package com.zy.connectionPool;

/**
 * Created by ecfgikd on 2017/10/19.
 */
public class  ConnectionFactory implements ObjectFactory<Connection> {

    private Connection conn;


    @Override
    public Connection create() {
        conn = new Connection();
        return conn;
    }

    @Override
    public void desctoryObject(Connection instance) {
        instance.destroyConnection();
    }
}
