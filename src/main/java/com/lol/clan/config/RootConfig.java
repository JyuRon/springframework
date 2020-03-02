package com.lol.clan.config;



import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages= {"com.lol.clan.sample"})
@MapperScan(basePackages= {"com.lol.clan.mapper"})
public class RootConfig {
	
	
	//커넥션 풀 설정 : 동시접속
	@Bean
	public DataSource dataSource() {
		
		HikariConfig hikariConfig = new HikariConfig();
		//hikariConfig.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		//hikariConfig.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:orcl");
		
		
		//log4j 설정
		hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
		hikariConfig.setJdbcUrl("jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl");
		
		
		hikariConfig.setUsername("dodam");
		hikariConfig.setPassword("1234");
		
		HikariDataSource dataSource = new HikariDataSource(hikariConfig);
		
		return dataSource;
	}
	
	
	
	//Mybatis
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource());
		
		return (SqlSessionFactory) sqlSessionFactory.getObject();
	}
	

}
