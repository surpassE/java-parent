package com.sirding.spring.rmi;

import org.apache.log4j.Logger;
import org.springframework.remoting.rmi.RmiServiceExporter;

public class Server {

	Logger logger = Logger.getLogger(Server.class);
	
	public static void main(String[] args) throws Exception {
		RmiServiceExporter rse = new RmiServiceExporter();
		rse.setService(new RmiServiceImpl());
		rse.setServiceName("rmiService");
		rse.setServiceInterface(RmiService.class);
		rse.setRegistryPort(10000);
		rse.afterPropertiesSet();
		System.out.println("start......");
	}
}
