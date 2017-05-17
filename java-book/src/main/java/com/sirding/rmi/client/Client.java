package com.sirding.rmi.client;

import java.rmi.Naming;

import com.sirding.rmi.CalcService;

public class Client {

	private static final String HOST = "rmi://127.0.0.1:10000/";
	public static void main(String[] args) throws Exception {
		CalcService calcService = (CalcService)Naming.lookup(HOST + "calcService");
		int sum = calcService.add(1, 2);
		System.out.println("1 + 2 = " + sum);
	}
}
