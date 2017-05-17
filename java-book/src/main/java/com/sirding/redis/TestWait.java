package com.sirding.redis;

public class TestWait {

	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("调用当前线程的wait()方法");
				synchronized (Thread.currentThread()) {
					Thread.currentThread().notifyAll();
				}
				System.out.println("over.....");
			}
		}).start();
	}
}
