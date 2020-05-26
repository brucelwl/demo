package com.example.demo.status;

import com.example.demo.status.info.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.lang.management.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class SystemInfoCollector implements InfoCollector {

    long MB = 1024 * 1024;

    private boolean m_dumpLocked;

    private String m_jars;

    private String m_dataPath = "/data";

    SystemInfo systemInfo = new SystemInfo();

    public SystemInfo collect() {

        systemInfo.setTimestamp(new Date());

        systemInfo.setDisk(this.collectDiskInfo());

        systemInfo.setOs(this.collectOs());

        systemInfo.setRuntime(this.collectRuntime());

        systemInfo.setMemory(this.collectMemoryInfo());

        systemInfo.setThread(this.collectThreadsInfo());

        systemInfo.setMessage(this.collectMessageInfo());

        //systemInfo.setExtensions();

        return systemInfo;
    }


    @Override
    public RuntimeInfo collectRuntime() {
        RuntimeInfo info = new RuntimeInfo();

        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        //start time of the Java virtual machine in milliseconds.
        info.setStartTime(runtimeMXBean.getStartTime());
        info.setStart(new Date(runtimeMXBean.getStartTime()));

        //uptime of the Java virtual machine in milliseconds.
        info.setUpTime(runtimeMXBean.getUptime());

        //TODO 存的啥 ???
        List<String> inputArguments = runtimeMXBean.getInputArguments();
        //long pid = runtimeMXBean.getPid(); api jdk10
        //Map<String, String> systemProperties = runtimeMXBean.getSystemProperties();

        info.setJavaClasspath(m_jars);
        info.setJavaVersion(System.getProperty("java.version"));
        info.setUserDir(System.getProperty("user.dir"));
        info.setUserName(System.getProperty("user.name"));

        return info;
    }

    static boolean isSunOsMXBean = false;
    static Class<?> sunOsMXBeanClass;

    static {
        try {
            String sunOsMXBeanFullClass = "com.sun.management.OperatingSystemMXBean";
            isSunOsMXBean = ClassUtils.isPresent(sunOsMXBeanFullClass, ClassUtils.getDefaultClassLoader());
            if (isSunOsMXBean) {
                sunOsMXBeanClass = Thread.currentThread().getContextClassLoader().loadClass(sunOsMXBeanFullClass);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public OsInfo collectOs() {
        OsInfo osInfo = new OsInfo();

        OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();

        osInfo.setArch(osMXBean.getArch());
        osInfo.setName(osMXBean.getName());
        osInfo.setVersion(osMXBean.getVersion());
        osInfo.setAvailableProcessors(osMXBean.getAvailableProcessors());
        osInfo.setSystemLoadAverage(osMXBean.getSystemLoadAverage());

        Extension systemExtension = systemInfo.findOrCreateExtension("System");
        systemExtension.findOrCreateExtensionDetail("SystemLoadAverage").setValue(osMXBean.getSystemLoadAverage());

        //for Sun JDK
        if (isSunOsMXBean && sunOsMXBeanClass.isInstance(osMXBean)) {
            com.sun.management.OperatingSystemMXBean sunOsMXBean = (com.sun.management.OperatingSystemMXBean) osMXBean;

            long freePhysicalMemorySize = sunOsMXBean.getFreePhysicalMemorySize();
            long freeSwapSpaceSize = sunOsMXBean.getFreeSwapSpaceSize();

            osInfo.setTotalPhysicalMemory(sunOsMXBean.getTotalPhysicalMemorySize());
            osInfo.setFreePhysicalMemory(freePhysicalMemorySize);

            osInfo.setTotalSwapSpace(sunOsMXBean.getTotalSwapSpaceSize());
            osInfo.setFreeSwapSpace(freeSwapSpaceSize);

            osInfo.setCommittedVirtualMemory(sunOsMXBean.getCommittedVirtualMemorySize());

            //当前Java进程CPU负载,jdk1.7新增的api
            osInfo.setProcessCpuLoad(sunOsMXBean.getProcessCpuLoad());
            osInfo.setProcessCpuTime(sunOsMXBean.getProcessCpuTime());

            //系统CPU负载,jdk1.7新增的api
            systemExtension.findOrCreateExtensionDetail("SystemLoadAverage").setValue(sunOsMXBean.getSystemCpuLoad());
            systemExtension.findOrCreateExtensionDetail("FreePhysicalMemory").setValue(freePhysicalMemorySize);
            systemExtension.findOrCreateExtensionDetail("FreeSwapSpaceSize").setValue(freeSwapSpaceSize);
        }
        return osInfo;
    }

    @Override
    public DiskInfo collectDiskInfo() {
        DiskInfo diskInfo = new DiskInfo("MB");

        File[] roots = File.listRoots();
        if (roots != null) {
            for (File disk : roots) {
                diskInfo.addDiskVolume(new DiskInfo.DiskVolumeInfo(disk.getAbsolutePath()));
            }
        }

        File data = new File(m_dataPath);
        if (data.exists()) {
            diskInfo.addDiskVolume(new DiskInfo.DiskVolumeInfo(data.getAbsolutePath()));
        }

        List<DiskInfo.DiskVolumeInfo> diskVolumes = diskInfo.getDiskVolumes();
        for (DiskInfo.DiskVolumeInfo diskVolume : diskVolumes) {
            File volume = new File(diskVolume.getDiskId());

            diskVolume.setTotal(volume.getTotalSpace() / MB);
            diskVolume.setFree(volume.getFreeSpace() / MB);
            diskVolume.setUsable(volume.getUsableSpace() / MB);

            Extension diskExtension = systemInfo.findOrCreateExtension("Disk");
            diskExtension.setDescription("Free");
            Extension.ExtensionDetail extensionDetail = diskExtension.findOrCreateExtensionDetail(diskVolume.getDiskId() + " Free");
            extensionDetail.setValue(volume.getFreeSpace() * 1.0 / MB);
        }
        return diskInfo;
    }

    @Override
    public MemoryInfo collectMemoryInfo() {
        MemoryInfo memoryInfo = new MemoryInfo("MB");
        Runtime runtime = Runtime.getRuntime();

        memoryInfo.setMax(runtime.maxMemory() / MB);
        memoryInfo.setTotal(runtime.totalMemory() / MB);
        memoryInfo.setFree(runtime.freeMemory() / MB);

        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        memoryInfo.setHeapUsage(memoryMXBean.getHeapMemoryUsage().getUsed() / MB);
        memoryInfo.setNonHeapUsage(memoryMXBean.getNonHeapMemoryUsage().getUsed() / MB);

        memoryMXBean.getHeapMemoryUsage().getCommitted();
        memoryMXBean.getNonHeapMemoryUsage().getMax();

        Extension gcExtension = systemInfo.findOrCreateExtension("GC");

        List<GarbageCollectorMXBean> gcMxBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcMxBean : gcMxBeans) {
            if (gcMxBean.isValid()) {
                String name = gcMxBean.getName();
                long collectionCount = gcMxBean.getCollectionCount();
                long collectionTime = gcMxBean.getCollectionTime();

                GcInfo gcInfo = new GcInfo(name, collectionCount, collectionTime);
                memoryInfo.addGc(gcInfo);

                gcExtension.findOrCreateExtensionDetail(name + "Count").setValue(collectionCount);
                gcExtension.findOrCreateExtensionDetail(name + "Time").setValue(collectionTime);
            }
        }
        Extension heapUsage = systemInfo.findOrCreateExtension("JVMHeap");
        List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean memoryPoolMXBean : memoryPoolMXBeans) {
            String name = memoryPoolMXBean.getName();
            long used = memoryPoolMXBean.getUsage().getUsed();
            heapUsage.findOrCreateExtensionDetail(name).setValue(used * 1.0 / MB);
        }
        return memoryInfo;
    }

    @Override
    public ThreadsInfo collectThreadsInfo() {
        ThreadsInfo threadsInfo = new ThreadsInfo();

        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        threadMXBean.setThreadContentionMonitoringEnabled(true);
        ThreadInfo[] threads;
        if (m_dumpLocked) {
            threads = threadMXBean.dumpAllThreads(true, true);
        } else {
            threads = threadMXBean.dumpAllThreads(false, false);
        }
        threadsInfo.setCount(threadMXBean.getThreadCount());
        threadsInfo.setDaemonCount(threadMXBean.getDaemonThreadCount());
        threadsInfo.setPeekCount(threadMXBean.getPeakThreadCount());
        threadsInfo.setTotalStartedCount((int) threadMXBean.getTotalStartedThreadCount());

        return threadsInfo;
    }

    @Override
    public MessageInfo collectMessageInfo() {
        return null;
    }

    @Override
    public Map<String, Extension> collectExtensions() {
        return null;
    }
}
