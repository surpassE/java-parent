package com.sirding.spring;

import org.springframework.beans.factory.InitializingBean;

public class User implements InitializingBean {

	private String name;
	private String value;
	public void init(){
		System.out.println("init....");
		this.value = "init_Hello";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("after...");
	}
	
}
