package com.framework.redis;

/**
 * 分布式锁
 * @author 	 zc.ding
 * @since 	 
 * @version  1.1
 */
public interface Lock {
	/**
	 * 获取锁，一次性无等待
	 */
	boolean lock();

	/**
	 * 尝试获取锁，可以设置等待时间
	 * 
	 * @param timeout
	 */
	boolean tryLock(int timeout);
	
	/**
	 * 释放锁
	 */
	void unlock();
}
