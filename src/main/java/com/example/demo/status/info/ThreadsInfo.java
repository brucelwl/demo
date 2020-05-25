package com.example.demo.status.info;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by bruce on 2020/5/25 22:19
 */

@Setter
@Getter
public class ThreadsInfo {

    private int m_count;

    private int m_daemonCount;

    private int m_peekCount;

    private int m_totalStartedCount;

    private int m_catThreadCount;

    private int m_pigeonThreadCount;

    private int m_httpThreadCount;

    private String m_dump;
}

