package com.example.demo.status.info;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by bruce on 2020/5/25 22:19
 */

@Setter
@Getter
public class ThreadsInfo {

    private int count;

    private int daemonCount;

    private int peekCount;

    private int totalStartedCount;

    private int catThreadCount;

    private int pigeonThreadCount;

    private int httpThreadCount;

    private String dump;
}

