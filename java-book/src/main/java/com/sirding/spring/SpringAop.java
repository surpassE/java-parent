package com.sirding.spring;

import org.apache.log4j.Logger;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.config.AopNamespaceHandler;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.config.ContextNamespaceHandler;
import org.springframework.context.weaving.LoadTimeWeaverAwareProcessor;
import org.springframework.transaction.annotation.SpringTransactionAnnotationParser;
import org.springframework.transaction.config.TxNamespaceHandler;
import org.springframework.transaction.interceptor.TransactionInterceptor;

public class SpringAop {

	Logger logger = Logger.getLogger(SpringAop.class);
	
	public void test1(){
		AopNamespaceHandler aopNamespaceHandler = null;
		AnnotationAwareAspectJAutoProxyCreator aaajapc = null;
		BeanPostProcessor bpp = null;
		ContextNamespaceHandler handler= null;
		LoadTimeWeaverAwareProcessor ltw = null;
		TxNamespaceHandler txHandler = null;
		SpringTransactionAnnotationParser sta = null;
		TransactionInterceptor ti = null;
		
		logger.debug(ti);
		logger.debug(sta);
		logger.debug(txHandler);
		logger.debug(ltw);
		logger.debug(handler);
		logger.debug(aopNamespaceHandler);
		logger.debug(aaajapc);
		logger.debug(bpp);
		
	}
}
