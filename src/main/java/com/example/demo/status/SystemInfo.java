package com.example.demo.status;

import com.example.demo.status.info.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Setter
@Getter
public class SystemInfo {

    private java.util.Date timestamp;

    private RuntimeInfo runtime;

    private OsInfo os;

    private DiskInfo disk;

    private MemoryInfo memory;

    private ThreadsInfo thread;

    private MessageInfo message;

    private Map<String, Extension> extensions = new LinkedHashMap<>();

    public Extension findOrCreateExtension(String id){
       return extensions.computeIfAbsent(id, Extension::new);
    }



}
