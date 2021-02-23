package com.example.demo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by bruce on 2021/1/26 21:31
 */
public class Demo {

    public static void main(String[] args) {

        // PriorityBlockingQueue<Address> priorityBlockingQueue = new PriorityBlockingQueue<>();
        // Address address = new Address();
        // priorityBlockingQueue.add(address);

        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String,String>(2, 1, true){
            private static final long serialVersionUID = -7970765140086018469L;

            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > 2;
            }
        };

        linkedHashMap.put("cc", "ccc");
        linkedHashMap.put("aa", "aaa");
        linkedHashMap.put("bb", "bbb");

        for (Map.Entry<String, String> entry : linkedHashMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println();

        String ccc = linkedHashMap.get("aa");
        System.out.println(ccc);

        System.out.println();

        for (Map.Entry<String, String> entry : linkedHashMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }



    }


}
