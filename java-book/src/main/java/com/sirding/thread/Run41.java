package com.sirding.thread;

public class Run41 {

	public static void main(String[] args) {
		MyService41 service = new MyService41();
		MyThread41 a1 = new MyThread41(service, "a1");
		MyThread41 a2 = new MyThread41(service, "a2");
		MyThread41 a3 = new MyThread41(service, "a3");
		MyThread41 a4 = new MyThread41(service, "a4");
		MyThread41 a5 = new MyThread41(service, "a5");
		a1.start();
		a2.start();
		a3.start();
		a4.start();
		a5.start();
	}
}
