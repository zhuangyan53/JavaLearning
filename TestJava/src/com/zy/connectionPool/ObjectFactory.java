package com.zy.connectionPool;

/**
 * Created by ecfgikd on 2017/10/19.
 */
public interface ObjectFactory <E>{
    E create();
    void desctoryObject(E instance);
}
