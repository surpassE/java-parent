package com.sirding.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestJdkProxy {

	final static Logger logger = LogManager.getLogger(TestJdkProxy.class);
	
	public static void main(String[] args) throws Exception{
		InvocationHandler handler = new CustHandler(); 
		// 通过 Proxy 直接创建动态代理类实例
		DemoI proxy = (DemoI)Proxy.newProxyInstance(DemoI.class.getClassLoader(), 
			 new Class[] { DemoI.class }, handler);
		logger.debug(proxy.get("Hello"));
		logger.debug(proxy);
		
		
		InvocationHandler handler1 = new CustHandler(); 
		DemoI proxy1 = (DemoI)Proxy.newProxyInstance(DemoI.class.getClassLoader(), 
				 new Class[] { DemoI.class }, handler1);
		logger.debug(proxy1);
	}
	
	public static void testWeakHashMap(){
		Map<String, String> map = new WeakHashMap<>();
		map.put("", "");
	}
}
