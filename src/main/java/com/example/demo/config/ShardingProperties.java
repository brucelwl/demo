package com.example.demo.config;

import java.util.List;

/**
 * Created by bruce on 2019/6/15 0:13
 */
public class ShardingProperties {

    private Integer defaultDSIndex;
    private String basePackage;
    private String mapperLocations;
    private String typeAliasesPackage;
    private String markerInterface;
    private Integer defaultStatementTimeoutInSecond;
    private Boolean mapUnderscoreToCamelCase;
    private String configLocation;


    private List<MyDataSourceProperties> dataSources;


    public Integer getDefaultDSIndex() {
        return defaultDSIndex;
    }

    public void setDefaultDSIndex(Integer defaultDSIndex) {
        this.defaultDSIndex = defaultDSIndex;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public String getTypeAliasesPackage() {
        return typeAliasesPackage;
    }

    public void setTypeAliasesPackage(String typeAliasesPackage) {
        this.typeAliasesPackage = typeAliasesPackage;
    }

    public String getMarkerInterface() {
        return markerInterface;
    }

    public void setMarkerInterface(String markerInterface) {
        this.markerInterface = markerInterface;
    }

    public Integer getDefaultStatementTimeoutInSecond() {
        return defaultStatementTimeoutInSecond;
    }

    public void setDefaultStatementTimeoutInSecond(Integer defaultStatementTimeoutInSecond) {
        this.defaultStatementTimeoutInSecond = defaultStatementTimeoutInSecond;
    }

    public Boolean getMapUnderscoreToCamelCase() {
        return mapUnderscoreToCamelCase;
    }

    public void setMapUnderscoreToCamelCase(Boolean mapUnderscoreToCamelCase) {
        this.mapUnderscoreToCamelCase = mapUnderscoreToCamelCase;
    }

    public String getConfigLocation() {
        return configLocation;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    public List<MyDataSourceProperties> getDataSources() {
        return dataSources;
    }

    public void setDataSources(List<MyDataSourceProperties> dataSources) {
        this.dataSources = dataSources;
    }
}
