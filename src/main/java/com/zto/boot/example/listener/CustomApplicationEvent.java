package com.zto.boot.example.listener;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义事件类型
 *
 * Created by bruce on 2019/7/4 21:26
 */
public class CustomApplicationEvent extends ApplicationEvent {

    private static final long serialVersionUID = -8970400714747705154L;

    public CustomApplicationEvent(Object source) {
        super(source);
    }
}
