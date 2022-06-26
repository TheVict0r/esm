package com.epam.esm.config;

import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** Basic class for module CORE configuration */
@Configuration
@EnableTransactionManagement
public class CoreConfig {

  @Value("${spring.datasource.driver-class-name}")
  private String driver;

  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  @Value("${hibernate.dialect}")
  private String dialect;

  @Value("${hibernate.connection.pool_size}")
  private String poolSize;

  @Value("${hibernate.current_session_context_class}")
  private String currentSessionContextClass;

  @Value("${hibernate.show_sql}")
  private String showSql;

  @Value("${hibernate.format-sql}")
  private String formatSql;

  @Bean
  public KeyHolder keyHolder() {
    return new GeneratedKeyHolder();
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public DataSource dataSource() {
    HikariConfig config = new HikariConfig();
    config.setDriverClassName(driver);
    config.setJdbcUrl(url);
    config.setUsername(username);
    config.setPassword(password);
    return new HikariDataSource(config);
  }

  //  @Bean
  //  public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
  //    return new HibernateTransactionManager(sessionFactory);
  //  }

  @Bean
  public DataSourceTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean
  public SessionFactory sessionFactory() {
    SessionFactory sessionFactory;
    try {
      org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
      configuration.setProperties(getProperties());
      configuration.addAnnotatedClass(Certificate.class);
      configuration.addAnnotatedClass(Tag.class);
      sessionFactory = configuration.buildSessionFactory();
    } catch (Exception e) {
      throw new ExceptionInInitializerError(e);
    }
    return sessionFactory;
  }

  private Properties getProperties() {
    Properties properties = new Properties();
    properties.setProperty("connection.driver_class", driver);
    properties.setProperty("hibernate.connection.url", url);
    properties.setProperty("hibernate.connection.username", username);
    properties.setProperty("hibernate.connection.password", password);
    properties.setProperty("hibernate.dialect", dialect);
    properties.setProperty("hibernate.connection.pool_size", poolSize);
    properties.setProperty("hibernate.current_session_context_class", currentSessionContextClass);
    properties.setProperty("hibernate.show_sql", showSql);
    properties.setProperty("hibernate.format_sql", formatSql);
    return properties;
  }
}
