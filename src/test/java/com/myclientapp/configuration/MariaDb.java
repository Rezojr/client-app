package com.myclientapp.configuration;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class MariaDb {

    @Bean
    public MariaDB4jSpringService mariaDB4jSpringService() {
        return new MariaDB4jSpringService();
    }

    @Bean
    @Primary
    public DataSource dataSource(
            @Value("${app.mariaDB4j.databaseName}") String databaseName,
            DataSourceProperties dataSourceProperties) throws ManagedProcessException {

        mariaDB4jSpringService().getDB().createDB(databaseName);

        DBConfigurationBuilder config = mariaDB4jSpringService().getConfiguration();
        return DataSourceBuilder
                .create()
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword())
                .url(config.getURL(databaseName))
                .driverClassName(dataSourceProperties.getDriverClassName())
                .build();
    }
}
