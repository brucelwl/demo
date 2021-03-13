package com.example.demo;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

/**
 * Created by bruce on 2021/3/12 20:24
 */
public class FileWatchTest {

    @Test
    public void fileTest1() {
        String path = "D:/workspaces";
        WatchService watchService = null;
        try {
            watchService = FileSystems.getDefault().newWatchService();
            WatchKey watchKey = Paths.get(path).register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
            List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
            for (WatchEvent<?> watchEvent : watchEvents) {
                System.out.println(watchEvent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        WatchService finalWatchService = watchService;
        new Thread(new Runnable() {
            @Override
            public void run() {
                WatchKey watchKey = null;
                while (true) {
                    try {
                        if (((watchKey = finalWatchService.take()) != null)) {
                            List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
                            for (WatchEvent<?> watchEvent : watchEvents) {
                                if (watchEvent.kind().equals(StandardWatchEventKinds.ENTRY_CREATE)) {
                                    Path context = (Path) watchEvent.context();
                                    System.out.println("创建事件:" + context.getFileName());
                                } else if (watchEvent.kind().equals(StandardWatchEventKinds.ENTRY_DELETE)) {
                                    Path context = (Path) watchEvent.context();
                                    System.out.println("删除事件:" + context.getFileName());
                                } else if (watchEvent.kind().equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
                                    //同一次变更事件会执行两次
                                    Path context = (Path) watchEvent.context();
                                    System.out.println("变更事件:" + context.getFileName());
                                }
                            }
                            watchKey.reset();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void fileTest2() {
        String path = "D:/workspaces";
        FileAlterationObserver observer = new FileAlterationObserver(path,
                FileFilterUtils.and(FileFilterUtils.fileFileFilter(), FileFilterUtils.nameFileFilter("new.txt")));

        observer.addListener(new FileAlterationListenerAdaptor() {
            @Override
            public void onFileChange(File file) {
                System.out.println(file + " lastModified:" + file.lastModified());
            }
        });

        FileAlterationMonitor monitor = new FileAlterationMonitor(2000, observer);
        try {
            monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
