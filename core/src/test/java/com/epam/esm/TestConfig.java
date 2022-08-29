package com.epam.esm;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** Basic config class for unit and integration tests. */
@SpringBootApplication
@EnableTransactionManagement
public class TestConfig {

	/**
	 * Datasource for embedded database. Can be used for integration tests.
	 *
	 * @return datasource for embedded database
	 */
	// @Bean
	// public DataSource dataSourceTest() {
	// return new
	// EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScript("create_db.sql")
	// .addScript("data.sql").build();
	// }
	//
	// @Bean
	// public JdbcTemplate jdbcTemplate(DataSource dataSource) {
	// return new JdbcTemplate(dataSource);
	// }
}
