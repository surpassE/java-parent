package com.sirding.spring;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ObjectUtils;

@SuppressWarnings("deprecation")
public class SpringIOC {

	Logger logger = Logger.getLogger(SpringIOC.class);
	
	@Test
	public void test1(){
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinitions("test-core.xml");
		reader.loadBeanDefinitions(new ClassPathResource("test-core.xml"));
		this.showMsg(factory);
	}
	
	@Test
	public void test2(){
		BeanFactory factory = new XmlBeanFactory(new ClassPathResource("test-core.xml"));
		this.showMsg(factory);
	}
	
	@Test
	public void test3(){
		BeanFactory factory = new ClassPathXmlApplicationContext("test-core.xml");
		this.showMsg(factory);
	}
	
	private void showMsg(BeanFactory factory){
		User user = (User) factory.getBean("user");
		logger.debug(user);
		logger.debug(user.getName());
		logger.debug(user.getValue());
	}
	
	@Test
	public void test4(){
		User u0 = new User();
		User u = u0;
		u0 = null;
		logger.debug(u);
	}
	
	@Test
	public void test5(){
		logger.debug(this);
		logger.debug(ObjectUtils.identityToString(this));
		logger.debug(ObjectUtils.getIdentityHexString(this));
	}
	
}
