package com.sirding.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustHandler implements InvocationHandler{

	Logger logger = LogManager.getLogger(CustHandler.class);

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//		打印proxy会出现java.lang.StackOverflowError异常
		//		System.out.println(proxy);
		logger.trace(method);
		logger.trace(args);		
		if (Object.class.equals(method.getDeclaringClass())) {
			try {
				return method.invoke(this, args);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		if(args != null){
			for(Object obj : args){
				logger.debug(obj);
			}
		}
		return "我已经指定了代理";
	}

}
