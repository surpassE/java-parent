package com.sirding.thread;

public class MyThread41 extends Thread {

	private MyService41 service;
	
	public MyThread41(MyService41 service, String name){
		super();
		this.service = service;
		setName(name);
	}
	
	public void run() {
		service.testMethod();
	}
}
