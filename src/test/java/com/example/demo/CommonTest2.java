package com.example.demo;


import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * -Xms10m -Xmx10m -XX:MetaspaceSize=10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -version
 * <p>
 * Created by bruce on 2019/11/27 20:47
 */
public class CommonTest2 {
    //static {
    //    System.setProperty("jdk.internal.lambda.dumpProxyClasses", ".");
    //}

    public static void main(String[] args) throws IOException {

        Path path1 = Paths.get("D:\\workspaces\\demo\\src\\main\\resources\\data.txt");

        FileChannel fileChannel1 = FileChannel.open(path1, StandardOpenOption.WRITE, StandardOpenOption.READ);

        MappedByteBuffer mappedByteBuffer1 = fileChannel1.map(FileChannel.MapMode.READ_WRITE, 0, 8);

        mappedByteBuffer1.putLong(Long.MAX_VALUE);

        long aLong = mappedByteBuffer1.getLong(0);

        System.out.println(aLong);
        System.out.println(Long.MAX_VALUE);

    }



}
