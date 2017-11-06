package com.sirding.redis;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

public class TestRedisLock {
	private static final String HOST = "192.168.1.250";	// 127.0.0.1
	private static final Integer PORT = 6379;	// 2185
	Logger logger = Logger.getLogger(getClass());
	public static JedisPool pool;
	public static Jedis jedis;
	static RedisConnection connection;
	public static final Object LOCK = new Object();

	@BeforeClass
	public static void before(){
		if (pool == null) {  
			try {
				JedisPoolConfig config = new JedisPoolConfig();  
				//控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。  
				config.setMaxIdle(300);  
				config.setMaxTotal(1000);
				//表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；  
				config.setMaxWaitMillis(1000);
				//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；  
				config.setTestOnBorrow(true); 
				pool = new JedisPool(config, HOST, PORT);
				jedis = pool.getResource();
				JedisConnectionFactory jcf = new JedisConnectionFactory(config);
				JedisShardInfo shardInfo = new JedisShardInfo(HOST, PORT);
				jcf.setShardInfo(shardInfo);
				jcf.setUsePool(true);
				jcf.setHostName(HOST);
				jcf.setPort(2185);
				connection = jcf.getConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}  
	}

	@AfterClass
	public static void after(){
	}

	@Test
	public void testAdd(){
		Jedis jedis = pool.getResource(); 
		long count = jedis.setnx("test:", String.valueOf(System.currentTimeMillis()));
		
		long time = jedis.ttl("test");
		System.out.println("过期时间：" + time);
		
		count = jedis.expire("test", 12000);
		logger.debug(count);
		jedis.close();
		
	}
	
	@Test
	public void testTryLock()	{
		RedisLockUtil util = new RedisLockUtil();
		for(int i = 0; i < 10; i++){
			RedisLockThread redisLockThread = new RedisLockThread(util);
			Thread thread = new Thread(redisLockThread, "LOCK_THREAD_" + i);
			thread.start();
		}
		//监控redis连接池使用状态
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					System.out.println("当前存活的线程:" + pool.getNumActive());
					System.out.println("当前空闲的线程:" + pool.getNumIdle());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		try {
			Thread.sleep(1000 * 3600);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetJedis() throws Exception{
		Set<Jedis> set = new HashSet<>();
		long time = System.currentTimeMillis();
		for(int i = 0; i < 20; i++){
			set.add(pool.getResource());
		}
		System.out.println("活动连接数：" + pool.getNumActive());
		System.out.println("空闲连接数：" + pool.getNumIdle());
		System.out.println("获得100个jedis连接耗时[" + (System.currentTimeMillis() - time) + "]ms.");
		for(Jedis jedis : set){
			jedis.close();
			jedis.disconnect();
		}
		System.out.println("活动连接数：" + pool.getNumActive());
		System.out.println("空闲连接数：" + pool.getNumIdle());
		time = System.currentTimeMillis();
		for(int i = 0; i < 20; i++){
			set.add(pool.getResource());
		}
		System.out.println("再次获得100个jedis连接耗时[" + (System.currentTimeMillis() - time) + "]ms.");
		System.out.println("总连接数：" + pool.getNumActive());
		System.out.println("空闲连接数：" + pool.getNumIdle());
		Thread.sleep(100000);
	}
	
	static AtomicInteger count = new AtomicInteger(0);
	public static synchronized Jedis getJedis(){
		System.out.println("获得jedis连接" + count.incrementAndGet());
		return pool.getResource();
	}
	
	/**
	 *  @Description    : 模糊删除
	 *  @Method_Name    : testDel
	 *  @return         : void
	 *  @Creation Date  : 2017年11月1日 上午10:38:38 
	 *  @Author         : zhichaoding@hongkun.com zc.ding
	 */
	@Test
	public void testDel() {
		String pattern = "SUBMIT_TOKEN:*";
		Jedis jedis = pool.getResource(); 
		StringBuffer sb = new StringBuffer();
		sb.append(pattern);
		Set<byte[]> set = jedis.keys(sb.toString().getBytes());
		System.out.println(set.size());
		set.forEach(e -> jedis.del(e));
		
		System.out.println(jedis.keys("TCC:*").size());
	}
}
