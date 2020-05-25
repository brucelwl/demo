package com.example.demo.status;

import com.example.demo.status.info.*;

import java.util.Map;

public interface InfoCollector {

    RuntimeInfo collectRuntime();

    OsInfo collectOs();

    DiskInfo collectDiskInfo();

    MemoryInfo collectMemoryInfo();

    ThreadsInfo collectThreadsInfo();

    MessageInfo collectMessageInfo();

    Map<String, Extension> collectExtensions();

}
