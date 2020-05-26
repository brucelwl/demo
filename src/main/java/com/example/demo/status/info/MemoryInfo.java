package com.example.demo.status.info;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @see com.sun.management.GcInfo
 */
@Setter
@Getter
public class MemoryInfo {

    private long max;

    private long total;

    private long free;

    private long heapUsage;

    private long nonHeapUsage;

    private List<GcInfo> gcs = new ArrayList<>();

}
