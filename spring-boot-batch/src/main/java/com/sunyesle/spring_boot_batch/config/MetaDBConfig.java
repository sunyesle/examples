package com.sunyesle.spring_boot_batch.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class MetaDBConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.meta-db")
    public DataSource metaDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager metaTransactionManager() {
        return new DataSourceTransactionManager(metaDataSource());
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.data-db")
    public DataSource dataDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
}
