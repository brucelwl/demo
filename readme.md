Manifest-Version: 1.0

Start-Class: 应用的启动类

Main-Class: org.springframework.boot.loader.JarLauncher

SpringBoot打成jar包后,真正的jar启动类是{@link JarLauncher},
通过反射调用应用启动类的main(String[] args),
实则如果SpringBoot定义应用启动类的启动方法也是可以的  
@see ExecutableArchiveLauncher#getMainClass()  
@see MainMethodRunner#run()  

```
 <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-loader</artifactId>
      <scope>provided</scope>
 </dependency>
```

#### 查看远程调试命令
java -agentlib:jdwp=help

#### 远程调试应用启动命令
java -agentlib:jdwp=transport=dt_socket,server=y,address=5005 -jar demo.jar

#### 远程调试
1. 应用打成jar包,通过***远程调试应用启动命令***启动应用
2. idea中创建remote,设置远程连接参数,见图片springboot-loader-debug.png
3. 运行remote调试