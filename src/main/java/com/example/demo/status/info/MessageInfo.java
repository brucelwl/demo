package com.example.demo.status.info;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageInfo {
    private long produced;

    private long overflowed;

    private long bytes;
}
