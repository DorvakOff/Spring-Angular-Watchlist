package com.dorvak.webapp.moteur.configuration;

public class Configuration {

    private final String databaseUrl;
    private final String databaseUsername;
    private final String databasePassword;
    private final String databaseDriver;

    public Configuration() {
        this.databaseUrl = "jdbc:h2:file:./WEB-INF/Data/database.db";
        this.databaseDriver = "org.h2.Driver";
        this.databaseUsername = "SA";
        this.databasePassword = "";
    }

    public String getDatabaseDriver() {
        return databaseDriver;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }
}
