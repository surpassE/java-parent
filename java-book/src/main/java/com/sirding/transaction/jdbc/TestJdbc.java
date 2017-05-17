package com.sirding.transaction.jdbc;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestJdbc {

	static Logger logger = LogManager.getLogger(TestJdbc.class);
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		BeanFactory factory = new ClassPathXmlApplicationContext("classpath*:test-jdbc.xml");
		UserServiceImpl userService = (UserServiceImpl)factory.getBean("userService");
		List<User> list = userService.findUser();
		if(list != null){
			for(User user : list){
				logger.debug(user.getUserName());
			}
		}
		logger.debug(list);
	}
}
