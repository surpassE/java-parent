package com.sirding.spring.rmi;

import org.springframework.remoting.rmi.RmiProxyFactoryBean;

public class Client {

	public static void main(String[] args) throws Exception {
		RmiProxyFactoryBean client = new RmiProxyFactoryBean();
		client.setServiceUrl("rmi://127.0.0.1:10000/rmiService");
		client.setServiceInterface(RmiService.class);
		client.afterPropertiesSet();
		RmiService rmiService = (RmiService)client.getObject();
		System.out.println(rmiService.getMsg("world"));
	}
}
