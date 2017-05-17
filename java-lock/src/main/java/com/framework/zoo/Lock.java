package com.framework.zoo;

public interface Lock {

	/**
	 * 尝试获取锁，可以设置等待时间
	 * 
	 * @param timeout
	 */
	boolean tryLock();
	
	/**
	 * 释放锁
	 */
	void unlock();
}
