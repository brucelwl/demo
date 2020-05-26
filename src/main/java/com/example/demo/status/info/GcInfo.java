package com.example.demo.status.info;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GcInfo {
    private String name;

    private long count;

    private long time;

    public GcInfo() {
    }

    public GcInfo(String name, long count, long time) {
        this.name = name;
        this.count = count;
        this.time = time;
    }

}
