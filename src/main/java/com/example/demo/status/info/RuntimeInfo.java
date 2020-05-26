package com.example.demo.status.info;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class RuntimeInfo {

    private long startTime;

    private Date start;

    private long upTime;

    private String javaVersion;

    private String userName;

    private String userDir;

    private String javaClasspath;


}
