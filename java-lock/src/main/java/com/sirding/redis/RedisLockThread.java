package com.sirding.redis;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

public class RedisLockThread implements Runnable{
	public static AtomicInteger num = new AtomicInteger(0);
	Logger logger = Logger.getLogger(getClass());
	
	RedisLockUtilOnline oldLock = null;
	RedisLockUtil newLock = null;
	private CountDownLatch latch; 

	RedisLockThread(RedisLockUtil util, CountDownLatch latch){
		this.newLock = util;
		this.latch = latch;
	}
	
	RedisLockThread(RedisLockUtilOnline util, CountDownLatch latch){
		this.oldLock = util;
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			final String key = "MY.LOCK";
			final int expire = 100;
			if(newLock != null){
				newLock.tryLock(key, expire);
			}
			if(oldLock != null){
				oldLock.tryLock(key, expire);
			}
			logger.debug("============第[" + RedisLockThread.num.incrementAndGet() + "]个线程=====");
			logger.debug("我已经拿到锁了:" + Thread.currentThread().getName());
			Thread.sleep(500);
			if(newLock != null){
				newLock.freeLock(key);
			}
			if(oldLock != null){
				oldLock.freeLock(key);
			}
			logger.debug(Thread.currentThread().getName() + "：执行结束");
			latch.countDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
