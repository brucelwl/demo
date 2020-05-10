package com.example.demo;


import org.junit.Test;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

/**
 * -Xms10m -Xmx10m -XX:MetaspaceSize=10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -version
 * <p>
 * Created by bruce on 2019/11/27 20:47
 */
public class CommonTest {
    //static {
    //    System.setProperty("jdk.internal.lambda.dumpProxyClasses", ".");
    //}

    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {

        URI pathStr = CommonTest.class.getClassLoader()
                .getResource("json_propertysource.json")
                .toURI();

        Path path = Paths.get(pathStr);

        FileChannel fileChannel = FileChannel.open(path);

        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());

        CharBuffer decode = StandardCharsets.UTF_8.decode(mappedByteBuffer);

        System.out.println(decode.toString());

        Path path1 = Paths.get("data.txt");

        FileChannel fileChannel1 = FileChannel.open(path1, StandardOpenOption.WRITE, StandardOpenOption.READ);

        //FileChannel fileChannel1 = FileChannel.open(path1, StandardOpenOption.WRITE,
        //        StandardOpenOption.READ, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        MappedByteBuffer mappedByteBuffer1 = fileChannel1.map(FileChannel.MapMode.READ_WRITE, 0, fileChannel1.size());

        //char aChar = mappedByteBuffer1.getChar();
        //int anInt = mappedByteBuffer1.getInt();

        mappedByteBuffer1.put(StandardCharsets.UTF_8.encode(decode));


        File mark = new File("D:/data/appdatas/cat/cat-titans2_web.mark");

        RandomAccessFile m_markFile = new RandomAccessFile(mark, "rw");
        MappedByteBuffer m_byteBuffer = m_markFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 20);

        if (m_byteBuffer.limit() > 0) {
            int index = m_byteBuffer.getInt();
            long lastTimestamp = m_byteBuffer.getLong();

            System.out.println(lastTimestamp);
        }

    }


    /**
     * 数字格式化正常,正常执行
     */
    @Test
    public void test() throws InterruptedException, ParseException {
        DecimalFormat df = new DecimalFormat("0.0000");

        CountDownLatch countDownLatch = new CountDownLatch(1);

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                double v = ThreadLocalRandom.current().nextDouble(100000);
                DecimalFormat localdf = new DecimalFormat("0.0000");
                String right = localdf.format(v);
                try {
                    countDownLatch.await();

                    String s1 = df.format(v);
                    //String s = writer.writeValueAsString(userInfo);
                    System.out.println(s1 + " " + v);
                    Assert.isTrue(s1.equals(right), "数字格式化异常");
                    //System.out.println(s + " " + v);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        Thread.sleep(2000);

        countDownLatch.countDown();

        Thread.sleep(2000);

        System.out.println("运行结束");

    }

    /**
     * 线程不安全,抛出异常
     */
    @Test
    public void test5() throws InterruptedException {
        DecimalFormat df = new DecimalFormat("0.00");
        CountDownLatch countDownLatch = new CountDownLatch(1);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                String v = ThreadLocalRandom.current().nextDouble(100000) + "";
                try {
                    countDownLatch.await();

                    Number parse = df.parse(v);
                    System.out.println(v + " " + v + " Number:" + parse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        Thread.sleep(2000);
        countDownLatch.countDown();

        Thread.sleep(5000);
        System.out.println("运行结束");
    }


    /**
     * 验证每次new DecimalFormat 然后调用 parse() 性能
     */
    @Test
    public void test2() throws ParseException {
        long start = System.currentTimeMillis();
        int count = 100_0000;

        for (int i = 0; i < count; i++) {
            DecimalFormat df = new DecimalFormat("0.00");
            df.parse("125.178");
        }

        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * 验证DecimalFormat#clone() 调用 parse() 性能
     */
    @Test
    public void test3() throws ParseException {
        long start = System.currentTimeMillis();
        int count = 100_0000;

        DecimalFormat df = new DecimalFormat("0.00");

        for (int i = 0; i < count; i++) {
            ((DecimalFormat) df.clone()).parse("125.178");
        }

        System.out.println(System.currentTimeMillis() - start);
    }


    /**
     * 验证ThreadLocal绑定DecimalFormat, 和通过DecimalFormat#clone() 性能比较
     */
    @Test
    public void test6() throws ExecutionException, InterruptedException {

        final ThreadLocal<DecimalFormat> DECIMAL_FORMATTER = ThreadLocal.withInitial(() -> {
            //System.out.println("创建DecimalFormat");
            return new DecimalFormat("0.##");
        });

        //DecimalFormat decimalFormat = new DecimalFormat("0.##");

        ExecutorService executorService = Executors.newFixedThreadPool(50);
        ArrayList<Future<?>> futures = new ArrayList<>(1000);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 100_0000; i++) {
            Future<?> submit = executorService.submit(new Runnable() {
                @Override
                public void run() {
                    double v = ThreadLocalRandom.current().nextDouble(1000000);
                    DECIMAL_FORMATTER.get().format(v);
                    //((DecimalFormat) decimalFormat.clone()).format(v);
                }
            });
            futures.add(submit);
        }

        for (Future<?> future : futures) {
            Object o = future.get();
        }

        long l = System.currentTimeMillis() - start;

        System.out.println("耗时:" + l);

    }


    @Test
    public void test7() throws InterruptedException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        CountDownLatch countDownLatch = new CountDownLatch(1);

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                    String date = dateFormat.format(new Date());
                    System.out.println("Date: " + date);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        Thread.sleep(2000);
        countDownLatch.countDown();

        Thread.sleep(2000);
        System.out.println("运行结束");
    }


    @Test
    public void test8() throws InterruptedException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
        CountDownLatch countDownLatch = new CountDownLatch(1);

        long now = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    Date date1 = new Date(now + finalI * 60000);

                    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");

                    countDownLatch.await();

                    String date = ((SimpleDateFormat) dateFormat.clone()).format(date1);
                    //String date = dateFormat.format(date1);

                    dateFormat.parse("2020-05-10 20:42:15");

                    String formatDate = dateFormat1.format(date1);

                    System.out.println("Date: " + date + " " + formatDate + " " + formatDate.equals(date));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        Thread.sleep(2000);
        countDownLatch.countDown();

        Thread.sleep(2000);
        System.out.println("运行结束");
    }


}
