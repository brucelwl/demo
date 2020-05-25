package com.example.demo.status.info;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RuntimeInfo {

    private long startTime;

    private long upTime;

    private String javaVersion;

    private String userName;

    private String userDir;

    private String javaClasspath;


}
