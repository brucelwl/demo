package com.example.demo.status.info;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageInfo {
    private long m_produced;

    private long m_overflowed;

    private long m_bytes;
}
