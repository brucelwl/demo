package com.example.demo.status.info;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by bruce on 2020/5/25 22:21
 */
@Setter
@Getter
public class Extension {
    private String id;

    private String description;

    private Map<String, ExtensionDetail> details = new LinkedHashMap<>();

    private Map<String, String> dynamicAttributes = new LinkedHashMap<>();

    public Extension(String id) {
        this.id = id;
    }

    public ExtensionDetail findOrCreateExtensionDetail(String id) {

        return details.computeIfAbsent(id, ExtensionDetail::new);

    }


    @Setter
    @Getter
    public static class ExtensionDetail {

        private String id;

        private double value;

        private Map<String, String> m_dynamicAttributes = new LinkedHashMap<>();

        public ExtensionDetail(String id) {
            this.id = id;
        }
    }


}
