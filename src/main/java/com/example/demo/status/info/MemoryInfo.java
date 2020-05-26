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

    private String unit;

    private long max;

    private long total;

    private long free;

    private long heapUsage;

    private long nonHeapUsage;

    private List<GcInfo> gcs = new ArrayList<>();

    public MemoryInfo(String unit) {
        this.unit = unit;
    }

    public MemoryInfo addGc(GcInfo gc) {
        gcs.add(gc);
        return this;
    }
}
