package com.example.demo.status.info;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GcInfo {
    private String name;

    private long count;

    private long time;
}
