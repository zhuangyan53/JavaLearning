package com.zy.jmx;

import javax.management.*;

/**
 * Created by ecfgikd on 2017/9/20.
 */
public class DynamicMBeanTest implements DynamicMBean {

    private String name;

    private void println(){
        System.out.println(this.name);
    }
    @Override
    public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
        if(attribute.equals("name"))
            return this.name;
        else
            return "";
    }

    @Override
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
        if(attribute.getName().equals("name")){this.name=(String)attribute.getValue();}
    }

    @Override
    public AttributeList getAttributes(String[] attributes) {
        return null;
    }

    @Override
    public AttributeList setAttributes(AttributeList attributes) {
        return null;
    }

    @Override
    public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
        if(actionName.equals("println")){
            println();
        }
        return null;
    }

    @Override
    public MBeanInfo getMBeanInfo() {
        return null;
    }
}
