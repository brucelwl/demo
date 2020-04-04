package com.example.demo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by bruce on 2019/11/27 20:47
 */
public class CommonTest {

    public static void main(String[] args) throws InterruptedException {

        Map<String, Integer> accessMap = new LinkedHashMap<>(16, 0.75f, true);
        accessMap.put("c", 100);
        accessMap.put("d", 200);
        accessMap.put("a", 500);
        accessMap.get("c");
        accessMap.put("d", 300);
        for (Map.Entry<String, Integer> entry : accessMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

    }
}
