package com.example.demo;


import com.sun.tools.attach.VirtualMachine;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.File;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.Properties;

/**
 * Created by bruce on 2019/12/6 11:16
 */
public class VirtualMachineTest {

    public static void main(String[] args) throws Exception {

        VirtualMachine virtualMachine = VirtualMachine.attach("15008");
        String javaHome = virtualMachine.getSystemProperties().getProperty("java.home");
        System.out.println(javaHome);

        //让JVM加载jmx Agent
        String jmxAgent = javaHome + File.separator + "lib" + File.separator + "management-agent.jar";
        virtualMachine.loadAgent(jmxAgent, "com.sun.management.jmxremote");

        //获得连接地址
        Properties agentProperties = virtualMachine.getAgentProperties();
        String address = agentProperties.getProperty("com.sun.management.jmxremote.localConnectorAddress");
        virtualMachine.detach();

        //通过jxm address来获取RuntimeMXBean对象，从而得到虚拟机运行时相关信息
        JMXServiceURL jmxServiceURL = new JMXServiceURL(address);
        JMXConnector connector = JMXConnectorFactory.connect(jmxServiceURL);
        RuntimeMXBean runtimeMXBean = ManagementFactory.newPlatformMXBeanProxy(connector.getMBeanServerConnection(),
                "java.lang:type=Runtime", RuntimeMXBean.class);

        // 得到目标虚拟机占用cpu时间
        System.out.println(runtimeMXBean.getUptime());

    }

    public void management() {

        //用于Java虚拟机的类加载系统的管理接口。
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();

        //用于Java虚拟机的编译系统的管理接口。
        CompilationMXBean compilationMXBean = ManagementFactory.getCompilationMXBean();

        //用于Java虚拟机的垃圾回收的管理接口。
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();

        //内存管理器的管理接口。
        List<MemoryManagerMXBean> memoryManagerMXBeans = ManagementFactory.getMemoryManagerMXBeans();

        //Java虚拟机内存系统的管理接口。
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

        //内存池的管理接口
        List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();

        //用于操作系统的管理接口，Java虚拟机在此操作系统上运行。
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();

        //Java虚拟机的运行时系统的管理接口。
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

        //Java虚拟机线程系统的管理接口
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();


    }


}
