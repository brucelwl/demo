package com.example.demo;

import io.netty.util.HashedWheelTimer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by bruce on 2021/3/10 15:07
 */
public class Timer {

    @Test
    public void test2() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(100, TimeUnit.MILLISECONDS);

        System.out.println("start:" + LocalDateTime.now().format(formatter));

        hashedWheelTimer.newTimeout(timeout -> {
            System.out.println("task1:start" + LocalDateTime.now().format(formatter));
            Thread.sleep(3000);
            System.out.println("task1:end" + LocalDateTime.now().format(formatter));
        }, 3, TimeUnit.SECONDS);

        hashedWheelTimer.newTimeout(timeout -> System.out.println("task2:" + LocalDateTime.now().format(
                formatter)), 4, TimeUnit.SECONDS);
        Thread.sleep(10000);
    }


    @Test
    public void test1() {

        int[] arr = {1, 5, 5, 5, 6, 7, 8, 9};

        System.out.println(binarySearch(arr, 5));

    }

    int binarySearch(int[] arr, int key) {
        int low = 0; //数组最小索引值
        int high = arr.length - 1; //数组最大索引值
        while (low <= high) {
            int mid = (low + high + 1) / 2;
            if (key == arr[mid]) {
                return mid;
            }
            if (key > arr[mid]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    @Test
    public void addressTest1() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            System.out.println("IP地址：" + addr.getHostAddress() + "，主机名：" + addr.getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addressTest2() {
        try {
            Enumeration<NetworkInterface> faces = NetworkInterface.getNetworkInterfaces();
            while (faces.hasMoreElements()) { // 遍历网络接口
                NetworkInterface face = faces.nextElement();
                if (face.isLoopback() || face.isVirtual() || !face.isUp()) {
                    continue;
                }
                System.out.print("网络接口名：" + face.getDisplayName() + "，地址：");
                Enumeration<InetAddress> address = face.getInetAddresses();
                while (address.hasMoreElements()) { // 遍历网络地址
                    InetAddress addr = address.nextElement();
                    if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress() && !addr.isAnyLocalAddress()) {
                        System.out.print(addr.getHostAddress() + " ");
                    }
                }
                System.out.println("");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }



}
