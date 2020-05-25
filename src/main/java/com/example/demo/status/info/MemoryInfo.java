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

    private long m_max;

    private long m_total;

    private long m_free;

    private long m_heapUsage;

    private long m_nonHeapUsage;

    private List<GcInfo> m_gcs = new ArrayList<>();

}
