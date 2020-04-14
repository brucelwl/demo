package com.example.demo;




import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by bruce on 2020/4/10 10:21
 */
public class People implements Serializable {
    private static final long serialVersionUID = 2659082826995480601L;
    private int age;
    public String name;

    People(int age, String name) {
        this.age = age;
        this.name = name;
    }

    //private void writeObject(ObjectOutputStream out) {
    //    System.out.println("是否调用了我？");
    //}
    //
    //private void readObject(ObjectInputStream in) {
    //    System.out.println("是否调用了我？");
    //}

    //在执行out.write()方法时会出发这个方法，先调用writeReplace
    // 如果没有writeReplace那么将会调用writeObject方法
    public Object writeReplace() {
        System.out.println("调用了 writeReplace()方法");
        return new Kong("路人");
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "People{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
