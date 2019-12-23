package com.example.demo;

/**
 * Created by bruce on 2019/12/19 13:30
 */
public class Client {
    public static void main(String[] args) {
        HelloServiceImpl service = new HelloServiceImpl();
        while (true) {
            service.sayHello();
            try {
                synchronized (service) {
                    service.wait(3000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
