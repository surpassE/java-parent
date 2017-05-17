package com.sirding.redis;

import redis.clients.jedis.Jedis;

public class RedisLockThread implements Runnable{

	RedisLockUtil util = null;
	RedisLockThread(RedisLockUtil util){
		this.util = util;
	}
	
	@Override
	public void run() {
		final String key = "MY.LOCK";
		final int expire = 100;
		Jedis jedis = TestRedisLock.getJedis();
		util.tryLock(jedis, key, expire);
		try {
			System.out.println("我已经拿到锁了:" + Thread.currentThread().getName());
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		util.freeLock(jedis, key);
		System.out.println(Thread.currentThread().getName() + "：执行结束");
	}

	
}
