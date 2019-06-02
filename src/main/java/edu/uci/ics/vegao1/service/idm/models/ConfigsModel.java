package edu.uci.ics.vegao1.service.idm.models;

import java.util.Map;

public class ConfigsModel {
    private Map<String, String> serviceConfig;
    private Map<String, String> loggerConfig;
    private Map<String, String> databaseConfig;
    private Map<String, String> sessionConfig;

    public ConfigsModel() {
    }

    public Map<String, String> getServiceConfig() {
        return serviceConfig;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setServiceConfig(Map<String, String> service) {
        this.serviceConfig = service;
    }

    public Map<String, String> getLoggerConfig() {
        return loggerConfig;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setLoggerConfig(Map<String, String> loggerConfig) {
        this.loggerConfig = loggerConfig;
    }

    public Map<String, String> getDatabaseConfig() {
        return databaseConfig;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setDatabaseConfig(Map<String, String> databaseConf) {
        this.databaseConfig = databaseConf;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public Map<String, String> getSessionConfig() {
        return sessionConfig;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setSessionConfig(Map<String, String> sessionConfig) {
        this.sessionConfig = sessionConfig;
    }
}