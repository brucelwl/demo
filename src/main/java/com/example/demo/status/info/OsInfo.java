package com.example.demo.status.info;

import com.example.demo.config.UseDefaultJsonSer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OsInfo {

    /** 操作系统名称 */
    private String name;

    /** 操作系统体系结构 */
    private String arch;

    /** 操作系统版本 */
    private String version;

    /** Java虚拟机可用的处理器数 */
    private int availableProcessors;

    /** 最后一分钟的系统平均负载, 如果负载平均值不可用，则返回负值 */
    private double systemLoadAverage;

    /** 当前Java进程CPUTime,单位:nanoseconds */
    private long processCpuTime;

    /** 当前Java进程CPU负载 */
    @UseDefaultJsonSer
    private double processCpuLoad;

    /** 以字节为单位的物理内存总量。 */
    private long totalPhysicalMemory;

    /** 可用物理内存的字节数 */
    private long freePhysicalMemory;

    /** 保证运行进程可用的虚拟内存量（字节），如果不支持此操作，则返回-1 */
    private long committedVirtualMemory;

    /** 交换空间的总大小（字节）。 */
    private long totalSwapSpace;

    /** 可用交换空间的字节数 */
    private long freeSwapSpace;


}
