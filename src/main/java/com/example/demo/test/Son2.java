package com.example.demo.test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class Son2 extends Father {

    protected void thinking() {
        //实现祖父类的thinking(),打印 I 'm grandFather
        try {
            MethodType mt = MethodType.methodType(void.class);
            MethodHandle md = MethodHandles.lookup().findSpecial(GrandFather.class, "thinking", mt, this.getClass());
            md.invoke(this);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
