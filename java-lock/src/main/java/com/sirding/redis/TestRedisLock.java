package com.sirding.redis;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class TestRedisLock {
	Logger logger = Logger.getLogger(getClass());
	
	int len = 10;
	
	/**
	 * 测试结果
	 * 		10		20		50		100
	 * 旧	7385	14355	33704	64674
	 * 新	7128	14059	32464	65729
	 * 
	 * 预热redis
	 * 旧	
	 * 新	5880	13129	42561
	 */
	@Test
	public void testOldLock() throws Exception{
		warmUp();
		CountDownLatch latch = new CountDownLatch(len);  
		ExecutorService exec = Executors.newCachedThreadPool();
//		RedisLockUtil util = new RedisLockUtil();
		RedisLockUtilOnline util = new RedisLockUtilOnline();
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < len; i++) {
			RedisLockThread redisLockThread = new RedisLockThread(util, latch);
			Thread thread = new Thread(redisLockThread, "LOCK_THREAD_" + i);
			exec.submit(thread);  
		}  
		//监控redis连接池使用状态
		new MonitorThread().start();
		exec.shutdown();  
		latch.await();
		logger.debug("所有任务执行完毕 :" + (System.currentTimeMillis() - startTime));  
		Thread.sleep(1000 * 3600);
	}
	
	public void testNewLock() throws Exception{
		CountDownLatch latch = new CountDownLatch(len);  
		ExecutorService exec = Executors.newCachedThreadPool();
		RedisLockUtil util = new RedisLockUtil();
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < len; i++) {
			RedisLockThread redisLockThread = new RedisLockThread(util, latch);
			Thread thread = new Thread(redisLockThread, "LOCK_THREAD_" + i);
			exec.submit(thread);  
		}  
		//监控redis连接池使用状态
		new MonitorThread().start();
		exec.shutdown();  
		latch.await();
		logger.debug("所有任务执行完毕 :" + (System.currentTimeMillis() - startTime));  
		Thread.sleep(1000 * 3600);
	}
	
	/**
	 * 预热redis连接
	 * @author	 zc.ding
	 * @since 	 
	 */
	public void warmUp(){
		Set<Jedis> set = new HashSet<>();
		for(int i = 0; i < len; i++){
			set.add(RedisFactory.getPool().getResource());
		}
		logger.debug("活动连接数：" + RedisFactory.getPool().getNumActive());
		logger.debug("空闲连接数：" + RedisFactory.getPool().getNumIdle());
		for(Jedis jedis : set){
			jedis.close();
//			jedis.disconnect();
		}
		logger.debug("活动连接数：" + RedisFactory.getPool().getNumActive());
		logger.debug("空闲连接数：" + RedisFactory.getPool().getNumIdle());
		logger.debug("redis预热结束");
	}
	
	@Test
	public void testN(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (Thread.currentThread()) {
					try {
						Thread.currentThread().wait(3000);
						logger.debug("我醒了");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
