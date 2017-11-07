package com.zy.innerClass;

/**
 * Created by ecfgikd on 2017/11/6.
 */
public class NimingInner {

    public Wrapper wrapping(int x){
        return new Wrapper(x){
          public int value(){
              return super.value()*47;
          }
        };
    }

    public static void main(String args []){
        NimingInner inner = new NimingInner();
        Wrapper w =inner.wrapping(2);
        System.out.println(w.value());
    }
}

class Wrapper{
    private int i;
    public Wrapper(int x){i=x;}
    public int value(){return i;}
}
