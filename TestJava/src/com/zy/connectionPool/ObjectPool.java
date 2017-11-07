package com.zy.connectionPool;

import java.util.concurrent.*;

/**
 * Created by ecfgikd on 2017/10/19.
 * idle timeout. max number of object. timeout.
 */
public class ObjectPool <E>{
    private final BlockingQueue <WrapperObject> queue;
    private Long timeout= 1000L;
    private Long idleTimeout = 0L;
    private Integer coreSize = 0;
    private Integer maxObjectSize = 0;
    private final Semaphore permit;
    private final ObjectFactory <E> factory;
    private static ScheduledExecutorService exe = Executors.newScheduledThreadPool(2);

    public ObjectPool(int coreSize, int maxObjectSize, long responseTimeout, ObjectFactory<E> factory){
        this.coreSize = coreSize;
        this.maxObjectSize = maxObjectSize;
        this.queue = new LinkedBlockingQueue<WrapperObject>(this.coreSize);
        this.timeout = responseTimeout;
        this.maxObjectSize = maxObjectSize;
        this.permit = new Semaphore(this.maxObjectSize);
        this.factory = factory;

    }
    public void destoryPool(){
        this.queue.clear();
        this.exe.shutdown();
    }

    public E getObject() throws InterruptedException, ObjectPoolException {

        if(!permit.tryAcquire(timeout,TimeUnit.MILLISECONDS)){
            throw new ObjectPoolException("Timeout for get Object permit");
        }
        E obj=null;
        WrapperObject<E> wrapperObj = queue.poll(timeout, TimeUnit.MILLISECONDS);
        if(wrapperObj!=null){
            if(wrapperObj.cancelIdleCountDown())
            obj = wrapperObj.getInstance();
            System.out.println("get object from queue");
        }

        if (obj == null && permit.availablePermits()>=0){
            //create new Object
            try {
                obj = factory.create();
                System.out.println("created new object");
            } catch (Exception e){
                permit.release();
            }
        }
        return obj;
    }
    public Boolean returnObject(E obj) throws InterruptedException {
        System.out.println("return object");
        Boolean result = queue.offer(new WrapperObject(obj,factory), timeout,TimeUnit.MILLISECONDS);
        if(!result){
            factory.desctoryObject(obj);
            System.out.println("failed to return object, just destroy it.");
        }else{
            System.out.println("return obj"+" back to queue");
        }
        permit.release();
        return result;
    }
    public void setIdleTimeout(long timeout){
        this.idleTimeout = timeout;

    }

    private class WrapperObject <E>{
        private Future future = null;
        private E instance;
        private final Long lastUsed;
        private final ObjectFactory<E> factory ;
        WrapperObject(E obj, ObjectFactory<E> factory) {
            this.factory = factory;
            lastUsed = System.currentTimeMillis();
            this.instance = obj;
            if (idleTimeout>0) startIdleCountDown();
        }


        public  E getInstance(){
            return instance;
        }

        synchronized public void idleDestory(){
            queue.remove(this);
            factory.desctoryObject(instance);
            this.instance= null;
        }
        synchronized private void startIdleCountDown(){
            if(idleTimeout>0){
                System.out.println("start to countdown!!!");
                future = exe.schedule(new destoryIdleObjectTask(this),idleTimeout,TimeUnit.MILLISECONDS);}

        }

        synchronized public Boolean cancelIdleCountDown() throws ObjectPoolException {
            if( (future==null && this.instance!=null)||(future!=null && !future.isDone() && future.cancel(true))){
                System.out.println("cancel the countdown!");
                return true;
            }else{
                throw new ObjectPoolException("cancel idle count down failed!");
            }
        }
    }

    private class destoryIdleObjectTask <E> implements Runnable {
        private WrapperObject<E> obj;
        destoryIdleObjectTask(WrapperObject<E> obj){
            this.obj = obj;
        }
        @Override
        public void run() {
            obj.idleDestory();
        }
    }



}










