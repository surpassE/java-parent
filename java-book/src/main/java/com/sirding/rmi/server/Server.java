package com.sirding.rmi.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.sirding.rmi.CalcService;

public class Server {
	private static final String HOST = "rmi://127.0.0.1:10000/";
	
	public static void main(String[] args) throws Exception {
		Registry registry = LocateRegistry.createRegistry(10000);
		String uri = "calcService";
		registry.bind(uri, new CalcServiceImpl());
		registry.rebind(uri, new CalcServiceImpl());
		System.out.println("start listener ......");
	}
	
	public static void bindService() throws Exception {
		CalcService calcService = new CalcServiceImpl();
		Naming.bind(HOST + "calcService", calcService);
		Naming.rebind(HOST + "calcService", calcService);
	}
}
