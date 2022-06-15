package com.epam.esm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Basic config class for unit and integration tests.
 */

@Configuration
@ComponentScan(basePackages = "com.epam.esm")
@EnableTransactionManagement
public class TestConfig {

    /**
     * Datasource for embedded database. Can be used for integration tests.
     *
     * @return datasource for embedded database
     */
    @Bean
    public DataSource dataSourceTest() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("create_db.sql")
                .addScript("data_entry.sql")
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}