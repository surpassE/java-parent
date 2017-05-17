package com.sirding.redis;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisFactory {

	Logger logger = Logger.getLogger(getClass());
	private static JedisPool pool;
	private static Object REDIS_LOCK = new Object();
	
	public static void init(){
		if (pool == null) {  
			try {
				JedisPoolConfig config = new JedisPoolConfig();  
				//控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。  
				config.setMaxIdle(300);  
				config.setMaxTotal(1000);
				//表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；  
				config.setMaxWaitMillis(1000000000);
				//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；  
				config.setTestOnBorrow(true); 
				pool = new JedisPool(config, "127.0.0.1", 2185);
//				jedis = pool.getResource();
//				JedisConnectionFactory jcf = new JedisConnectionFactory(config);
//				JedisShardInfo shardInfo = new JedisShardInfo("127.0.0.1", 2185);
//				jcf.setShardInfo(shardInfo);
//				jcf.setUsePool(true);
//				jcf.setHostName("127.0.0.1");
//				jcf.setPort(2185);
//				connection = jcf.getConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}  
	}
	
	/**
	 * @author	 zc.ding
	 * @since 	 2017年5月9日
	 * @return
	 */
	public static JedisPool getPool(){
		if(pool == null){
			synchronized (REDIS_LOCK) {
				if(pool == null){
					init();
				}
			}
		}
		return pool;
	}
	
	/**
	 * @author	 zc.ding
	 * @since 	 2017年5月9日
	 * @return
	 */
	public static synchronized Jedis getJedis(){
		if(pool == null){
			synchronized (REDIS_LOCK) {
				if(pool == null){
					init();
				}
			}
		}
		return pool.getResource();
	}

}
