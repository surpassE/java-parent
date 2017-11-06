package com.sirding.jdbc;

import java.io.Serializable;

public class DbMsg implements Serializable{

	
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 */
	    
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer age;
	
	public DbMsg() {}
	
	public DbMsg(String name, Integer age) {
		this.name = name;
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	
}
