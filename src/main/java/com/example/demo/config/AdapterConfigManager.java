package com.example.demo.config;

import org.slf4j.LoggerFactory;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by bruce on 2019/8/18 11:58
 */
public abstract class AdapterConfigManager {

    private static Pattern ARRAY_PATTERN = Pattern.compile("\\[([^]]+)");

    public static final String ARRAY_ADAPTER_SOURCE_NAME = "titans:AdapterPropertySource";
    public static final String ADAPTER_FILE_PATH = "classpath:/application-adapter.properties";

    private static final Map<String, PropertyOrigin> adapterPropertiesOrigin = new HashMap<>();

    //public static void loadAdapterProperties(ConfigurableEnvironment env) {
    //    DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
    //    Resource resource = defaultResourceLoader.getResource(ADAPTER_FILE_PATH);
    //
    //    String name = "titans:[application-adapter.properties]";
    //
    //    PropertiesPropertySourceLoader propertiesPropertySourceLoader = new PropertiesPropertySourceLoader();
    //    try {
    //        List<PropertySource<?>> sourceList = propertiesPropertySourceLoader.load(name, resource);
    //        if (sourceList.size() > 0) {
    //            PropertySource<?> adapterPropertySource = sourceList.get(0);
    //            env.getPropertySources().addLast(adapterPropertySource);
    //        }
    //    } catch (IOException e) {
    //        LoggerFactory.getLogger(AdapterConfigManager.class).error("count find {}", ADAPTER_FILE_PATH);
    //    }
    //
    //}

    public static void loadAdapterPropertySource(ConfigurableEnvironment env) {

        HashMap<String, Object> adapterSource = new HashMap<>();
        HashMap<String, String> oldNewMapping = new HashMap<>();

        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        Resource resource = defaultResourceLoader.getResource(ADAPTER_FILE_PATH);
        Properties newOldMapping = null;
        try {
            newOldMapping = PropertiesLoaderUtils.loadProperties(resource);
            newOldMapping.forEach((key, value) -> {
                String spel = (String) value;
                if (extractIndex(spel).size() > 0) {
                    String spelValue = spel.substring(spel.indexOf("${") + 2, spel.lastIndexOf("}"));
                    oldNewMapping.put(spelValue, (String) key);
                } else {
                    //String property = env.getProperty(String.valueOf(key), (String) null);
                    if (!value.equals(env.resolvePlaceholders((String) value))) {
                        adapterSource.put((String) key, value);
                    }
                }
            });
        } catch (IOException e) {
            LoggerFactory.getLogger(AdapterConfigManager.class).error("count find {}", ADAPTER_FILE_PATH);
        }

        List<? extends EnumerablePropertySource<?>> sources = env.getPropertySources().stream()
                .filter(propertySource -> propertySource instanceof EnumerablePropertySource)
                .map(item -> (EnumerablePropertySource<?>) item)
                .collect(Collectors.toList());

        HashMap<String, EnumerablePropertySource<?>> arraySourceRecord = new HashMap<>();

        for (EnumerablePropertySource<?> source : sources) {
            String[] propertyNames = source.getPropertyNames();
            for (String propertyName : propertyNames) {
                List<String> indexs = extractIndex(propertyName);
                if (indexs.size() > 0) {
                    String oldKeyTemplate = propertyName.replaceAll("\\[[^]]", "[%s");
                    String newkeyTemplate = oldNewMapping.get(oldKeyTemplate);
                    if (newkeyTemplate != null) {
                        String prefix = oldKeyTemplate.substring(0, oldKeyTemplate.indexOf("[") + 1);
                        //EnumerablePropertySource<?> firstArraySource = arraySourceRecord.putIfAbsent(prefix, source);
                        EnumerablePropertySource<?> firstArraySource = arraySourceRecord.computeIfAbsent(prefix, (key) -> source);

                        if (firstArraySource.getName().equals(source.getName())) {
                            String newkey = String.format(newkeyTemplate, indexs.toArray());
                            adapterSource.putIfAbsent(newkey, "${" + propertyName + "}");
                            adapterPropertiesOrigin.putIfAbsent(newkey, new PropertyOrigin(propertyName, source.getName()));
                        }
                    }
                }
            }
        }

        if (adapterSource.size() > 0) {
            env.getPropertySources().addLast(new OriginTrackedMapPropertySource(ARRAY_ADAPTER_SOURCE_NAME, adapterSource));
        }

    }

    /** 使用正则表达式提取中括号中的内容 */
    public static List<String> extractIndex(String msg) {
        List<String> list = new ArrayList<>(1);
        Matcher m = ARRAY_PATTERN.matcher(msg);
        while (m.find()) {
            //list.add(m.group().substring(1, m.group().length() - 1));
            list.add(m.group(1));
        }
        return list;
    }


    public static Map<String, PropertyOrigin> getAdapterPropertiesOrigin() {
        return adapterPropertiesOrigin;
    }
}
