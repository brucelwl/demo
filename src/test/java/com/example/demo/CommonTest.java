package com.example.demo;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicInteger;

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


}
