package com.t2Sharing.JMX.dynamicMbean;

import javax.management.*;
import java.lang.reflect.Constructor;
import java.util.Iterator;

/**
 * Created by win on 2017/11/8.
 */
public class HelloDynamciMBean implements DynamicMBean {
    private String className;
    private String description;
    private MBeanAttributeInfo[] attributes;
    private MBeanOperationInfo[] operations;
    private MBeanConstructorInfo[] constructors;
    private MBeanInfo mBeanInfo = null;
    private String name;

    public HelloDynamciMBean(){

        name = "DynamciMbean";


        init();
        buildDynamicMbean();

}

private void init(){
    className = this.getClass().getName();
    description = "Simple implementation of a MBean.";

    //initial attributes
    attributes = new MBeanAttributeInfo[1];
    //initial constructors
    constructors = new MBeanConstructorInfo[1];
    //initial method
    operations = new MBeanOperationInfo[1];


}

private void buildDynamicMbean(){
    Constructor[] thisconstructors = this.getClass().getConstructors();
    constructors[0] = new MBeanConstructorInfo("HelloDynamic(): Constructs a HelloDynamic object", thisconstructors[0]);

    //create attribute
    attributes[0] = new MBeanAttributeInfo("Name", "java.lang.String", "Name: name string.", true, true,false);

    //create operate method
    MBeanParameterInfo[] params = null;//no parameter
    operations[0] = new MBeanOperationInfo("print", "print(): print the name", params, "void", MBeanOperationInfo.INFO);

    //create mbean
    mBeanInfo = new MBeanInfo(className, description, attributes, constructors, operations, null);

}

    @Override
    public Object getAttribute(String attribute_name) throws AttributeNotFoundException, MBeanException, ReflectionException {
        if (attribute_name == null) {
            return null;
        }

        if (attribute_name.equals("Name")) {
            return name;
        }

        return null;
    }

    @Override
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
        if (attribute == null) {
            return;
        }

        String Name = attribute.getName();
        Object value = attribute.getValue();
        try {
            if (Name.equals("Name")) {
                // if null value, try and if the setter returns any exception
                if (value == null) {
                    name = null;
                    // if non null value, make sure it is assignable to the attribute
                } else if ((Class.forName("java.lang.String")).isAssignableFrom(value.getClass())) {
                    name = (String) value;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public AttributeList getAttributes(String[] attributeNames) {
        if (attributeNames == null) {
            return null;
        }

        AttributeList resultList = new AttributeList();
        // if attributeNames is empty, return anempty result list
        if (attributeNames.length == 0) {
            return resultList;
        }

        for (int i = 0; i < attributeNames.length; i++) {
            try {
                Object value = getAttribute(attributeNames[i]);
                resultList.add(new Attribute(attributeNames[i], value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultList;


    }

    @Override
    public AttributeList setAttributes(AttributeList attributes) {

        if (attributes == null) {
            return null;
        }

        AttributeList resultList = new AttributeList();
        // if attributeNames is empty, nothing more to do
        if (attributes.isEmpty()) {
            return resultList;
        }

        // for each attribute, try to set it and add to the result list if successfull
        for (Iterator i = attributes.iterator(); i.hasNext();) {
            Attribute attr = (Attribute) i.next();
            try {
                setAttribute(attr);
                String name = attr.getName();
                Object value = getAttribute(name);
                resultList.add(new Attribute(name, value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultList;

    }

    private void dynamicAddOperation() {

        init();
        operations = new MBeanOperationInfo[2];
        buildDynamicMbean();
        operations[1] = new MBeanOperationInfo("print1", "print1(): print the name", null, "void", MBeanOperationInfo.INFO);
        mBeanInfo = new MBeanInfo(className, description, attributes, constructors, operations, null);

    }

    @Override
    public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {

        if (actionName.equals("print")) {
            System.out.println("Hello, " + name + ", this is HelloDynamic!");

            //dynamic add a method
            dynamicAddOperation();
            return null;
        } else if (actionName.equals("print1")) {
            System.out.println("dynamically add a print1 method");
            return null;
        } else {
            // unrecognized operation name:
            throw new ReflectionException(new NoSuchMethodException(actionName), "Cannot find the operation " + actionName + " in " + className);
        }
    }

    @Override
    public MBeanInfo getMBeanInfo() {
        return mBeanInfo;
    }
}
