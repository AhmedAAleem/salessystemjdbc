package com.sales.jdbc.config;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@ConfigurationProperties
public class InvoiceConfiguration {

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String userName;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.driver-class-name}")
	private String driver;

	@Bean
	public DataSource dataSource() {
		
		try {
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName(driver);
			dataSource.setUrl(url); // Change this URL based on your database configuration
			dataSource.setUsername(userName); // Change this to your database username
			dataSource.setPassword(password); // Change this to your database password
			return dataSource;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}

	public boolean isDbConnected() {
		try (Connection conn = dataSource().getConnection()) {
			return true;
		} catch (SQLException e) {
			
			return false;
		}
	}


}
