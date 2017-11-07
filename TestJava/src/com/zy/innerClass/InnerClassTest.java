package com.zy.innerClass;

/**
 * Created by ecfgikd on 2017/10/27.
 */

public class InnerClassTest {
    private String s1 = "222";
    public Object getInner(){
        class InnerClass1{
            String s2 = s1;
        }
        return new InnerClass1();
    }
}
