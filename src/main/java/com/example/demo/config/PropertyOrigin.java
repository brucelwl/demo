package com.example.demo.config;

/**
 * Created by bruce on 2019/8/18 12:00
 */
public class PropertyOrigin {

    private String key;
    private String sourceName;

    public PropertyOrigin(String key, String sourceName) {
        this.key = key;
        this.sourceName = sourceName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }


}
