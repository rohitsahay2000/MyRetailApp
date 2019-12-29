package com.rohit.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.rohit")
public class ApplicationConfiguration {

	@Bean
	public DataSource dataSource() {

		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName("org.h2.Driver");
		driverManagerDataSource.setUrl("jdbc:h2:tcp://localhost/~/test");
		driverManagerDataSource.setUsername("sa");
		driverManagerDataSource.setPassword("");

		return driverManagerDataSource;
	}

	@Bean
	public NamedParameterJdbcTemplate namedParamJdbcTemplate() {
		NamedParameterJdbcTemplate namedParamJdbcTemplate = new NamedParameterJdbcTemplate(dataSource());
		return namedParamJdbcTemplate;
	}

	@Bean(name = "txManager")
	public PlatformTransactionManager txManager() {
		return new DataSourceTransactionManager(dataSource());
	}

}
