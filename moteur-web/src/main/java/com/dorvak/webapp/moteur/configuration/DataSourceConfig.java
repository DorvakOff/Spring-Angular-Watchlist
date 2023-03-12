package com.dorvak.webapp.moteur.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Autowired
    private com.dorvak.webapp.moteur.configuration.Configuration configuration;

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(configuration.getDatabaseDriver());
        dataSourceBuilder.url(configuration.getDatabaseUrl());
        dataSourceBuilder.username(configuration.getDatabaseUsername());
        dataSourceBuilder.password(configuration.getDatabasePassword());
        return dataSourceBuilder.build();
    }

}
