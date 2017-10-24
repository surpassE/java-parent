package com.sirding.spring;

import org.apache.log4j.Logger;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.config.AopNamespaceHandler;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.config.ContextNamespaceHandler;
import org.springframework.context.weaving.LoadTimeWeaverAwareProcessor;
import org.springframework.transaction.annotation.SpringTransactionAnnotationParser;
import org.springframework.transaction.config.TxNamespaceHandler;
import org.springframework.transaction.interceptor.TransactionInterceptor;

public class SpringAop {

	Logger logger = Logger.getLogger(SpringAop.class);

	public void test1() {
		AopNamespaceHandler aopNamespaceHandler = null;
		AnnotationAwareAspectJAutoProxyCreator aaajapc = null;
		BeanPostProcessor bpp = null;
		ContextNamespaceHandler handler = null;
		LoadTimeWeaverAwareProcessor ltw = null;
		TxNamespaceHandler txHandler = null;
		SpringTransactionAnnotationParser sta = null;
		ConfigurableListableBeanFactory cl = null;
		TransactionInterceptor ti = null;
		AnnotationAwareAspectJAutoProxyCreator aajpc = null;
		ApplicationContext ac = null;
		ac.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(null, null);
		ac.getAutowireCapableBeanFactory().initializeBean(null, null);
		ac.getAutowireCapableBeanFactory().configureBean(null, null);
		DefaultListableBeanFactory bf = (DefaultListableBeanFactory) ac.getParentBeanFactory();
		// bf.registerAlias(beanName, alias);
		BeanDefinitionBuilder dataSourceBuider = BeanDefinitionBuilder.genericBeanDefinition(String.class);
		BeanDefinition bean = dataSourceBuider.getBeanDefinition();
		logger.debug(ti);
		logger.debug(sta);
		logger.debug(txHandler);
		logger.debug(ltw);
		logger.debug(handler);
		logger.debug(aopNamespaceHandler);
		logger.debug(aaajapc);
		logger.debug(bpp);

	}

	public void iocBena(ApplicationContext applicationContext) {
		Object obj = new Object();
		//
		// DefaultListableBeanFactory beanFactory =
		// (DefaultListableBeanFactory)applicationContext.getParentBeanFactory();
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext
				.getAutowireCapableBeanFactory();
		// 根据obj的类型、创建一个新的bean、添加到Spring容器中，
		// 注意BeanDefinition有不同的实现类，注意不同实现类应用的场景
		BeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClassName(obj.getClass().getName());
		beanFactory.registerBeanDefinition(obj.getClass().getName(), beanDefinition);

		applicationContext.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(obj,
				obj.getClass().getName());
		beanFactory.registerSingleton(obj.getClass().getName(), obj);

	}
}
