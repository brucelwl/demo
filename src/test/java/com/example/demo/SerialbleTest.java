package com.example.demo;


import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * Created by bruce on 2020/4/10 10:22
 */
public class SerialbleTest {

    @Test
    public void testOut03() throws Exception {
        People p = new People(2, "小白");
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:\\temp01.txt"));
        out.writeObject(p);
        out.flush();
        out.close();
    }

    @Test
    public void testIn03() throws Exception {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\temp01.txt"));
        //Kong k = (Kong) in.readObject();
        People k = (People) in.readObject();
        in.close();
        System.out.println(k);
    }
}
