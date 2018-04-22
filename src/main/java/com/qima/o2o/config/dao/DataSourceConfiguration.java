package com.qima.o2o.config.dao;

import java.beans.PropertyVetoException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.qima.o2o.util.DESUtils;

@Configuration
//配置mybatis mapper的扫描路径
@MapperScan("com.qima.o2o.dao")
public class DataSourceConfiguration {

	@Value("${jdbc.driver}")
	private String jdbcDriver;
	@Value("${jdbc.url}")
	private String jdbcUrl;
	@Value("${jdbc.username}")
	private String jdbcUsername;
	@Value("${jdbc.password}")
	private String jdbcPassword;
	
	@Bean(name="dataSource")
	public ComboPooledDataSource createDataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass(jdbcDriver);
			dataSource.setJdbcUrl(jdbcUrl);
			dataSource.setUser(DESUtils.getDecryptString(jdbcUsername));
			dataSource.setPassword(DESUtils.getDecryptString(jdbcPassword));
			dataSource.setMaxPoolSize(30);
			dataSource.setMinPoolSize(10);
			dataSource.setAutoCommitOnClose(false);
			dataSource.setCheckoutTimeout(10000);
			dataSource.setAcquireRetryAttempts(2);
			
			return dataSource;
			
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		return null;
	}
}
