package com.sirding.cglibproxy;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class EnhancerDemo {

	static Logger logger = LogManager.getLogger(EnhancerDemo.class);
	
	public static void main(String[] args) {
		//定义增强器
		Enhancer enhancer = new Enhancer();
		//设置代理的基类
		enhancer.setSuperclass(EnhancerDemo.class);
		//设置代理target
		enhancer.setCallback(new MethodInteceptorImpl());
		//创建代理实例
		EnhancerDemo demo = (EnhancerDemo) enhancer.create();
		demo.test("dobe");
		logger.info("执行结束");
	}
	
	public void test(String name){
		logger.debug("I am test method......");
		logger.debug("Hello " + name);
	}
	
	public static class MethodInteceptorImpl implements MethodInterceptor{

		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable{
			logger.debug(method.getName());
			logger.debug(args);
			logger.debug(proxy.getSuperName());
			logger.debug("before ......");
			Object result = proxy.invokeSuper(obj, args);
			logger.debug("after ......");
			return result;
		}
		
		public void test(String name){
			logger.debug("I am sub test method......");
			logger.debug("child Hello " + name);
		}
	}
}
