package Test.connectionPool;

/**
 * Created by ecfgikd on 2017/10/19.
 */


import com.zy.connectionPool.*;
import com.zy.connectionPool.ObjectFactory;

import javax.naming.spi.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ObjectPoolTest {
    public static void main(String[] args) {
        ExecutorService exe = Executors.newCachedThreadPool();
        ObjectFactory factory = new ConnectionFactory();
        ObjectPool<Connection> pool1 = new ObjectPool(10,20,5000,factory);


        for (int i=0;i<100;i++){
            long t = new Random().nextInt(1000);
            exe.submit(()-> {
                    Connection obj;

                    try {
                        obj = pool1.getObject();
                        TimeUnit.MILLISECONDS.sleep(t);
                        pool1.returnObject(obj);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ObjectPoolException e) {
                        e.printStackTrace();
                    }
                }
            );
            try {
                TimeUnit.MILLISECONDS.sleep(t);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool1.destoryPool();
        exe.shutdown();

//        ObjectPool<Connection> pool2 = new ObjectPool(5,10,1000,factory);
//        pool2.setIdleTimeout(5000);
//        try {
//            Connection c1 = pool2.getObject();
//            pool2.returnObject(c1);
//            pool2.getObject();
//            pool2.returnObject(c1);
//            TimeUnit.SECONDS.sleep(3);
//            pool2.getObject();
//            pool2.destoryPool();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ObjectPoolException e) {
//            e.printStackTrace();
//        }
    }
}
