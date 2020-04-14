package com.example.demo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by bruce on 2020/4/10 10:21
 */
public class Kong implements Serializable {
    private static final long serialVersionUID = -7144694309484327560L;

    public String s;

    Kong(String s) {
        this.s = s;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        System.out.println("Kong 出");
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        System.out.println("Kong 入");
    }
}
