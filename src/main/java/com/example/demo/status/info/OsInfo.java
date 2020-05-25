package com.example.demo.status.info;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OsInfo {

    private String name;

    private String arch;

    private String version;

    private int availableProcessors;

    private double systemLoadAverage;

    private long processTime;

    private long totalPhysicalMemory;

    private long freePhysicalMemory;

    private long committedVirtualMemory;

    private long totalSwapSpace;

    private long freeSwapSpace;


}
