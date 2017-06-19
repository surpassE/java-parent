package com.sirding.transaction.jdbc;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class UserServiceImpl {

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public int saveUser(User user){
		return this.jdbcTemplate.update(
				"insert into user(name, email) value(?,?)", 
				new Object[]{user.getUserName(), user.getEmail()}
				);
	}
	
	public List<User> findUser(){
		return this.jdbcTemplate.query(
				"select id, user_name, email from user", 
				new UserMapper()
				);
	}
	
	public void demo(){
		
	}
}
