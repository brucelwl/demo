package com.example.demo.status;

import com.example.demo.status.info.*;
import lombok.Getter;
import lombok.Setter;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class SystemInfoCollector implements InfoCollector {

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

    @Override
    public OsInfo collectOs() {
        OsInfo osInfo = new OsInfo();

        OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();

        osInfo.setArch(osMXBean.getArch());

        Extension systemExtension = systemInfo.findOrCreateExtension("System");
        systemExtension.findOrCreateExtensionDetail("LoadAverage").setValue(osMXBean.getSystemLoadAverage());


        return osInfo;
    }

    @Override
    public DiskInfo collectDiskInfo() {
        return null;
    }

    @Override
    public MemoryInfo collectMemoryInfo() {
        return null;
    }

    @Override
    public ThreadsInfo collectThreadsInfo() {
        return null;
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
