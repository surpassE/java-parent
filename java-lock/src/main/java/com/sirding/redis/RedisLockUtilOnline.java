package com.sirding.redis;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

/**
 * 获得redis分布式锁
 * @author 	 zc.ding
 * @since 	 2017年4月25日
 * @version  1.1
 */
public class RedisLockUtilOnline {
	Logger logger = Logger.getLogger(getClass());
	/**
	 * 获得redis锁
	 * @author	 zc.ding
	 * @since 	 2017年4月25日
	 * @param key 锁的主键
	 * @param expire 锁的过期时间
	 * @return
	 */
	public boolean tryLock(String key, int expire){
		return this.tryLock(null, key, expire);
	}

	/**
	 * 获得redis锁
	 * @author	 zc.ding
	 * @since 	 2017年4月25日
	 * @param jedis redis连接
	 * @param key 锁的主键
	 * @param expire 锁的过期时间
	 * @return
	 */
	public boolean tryLock(Jedis jedis, String key, int expire){
		boolean locked = this.getLock(jedis, key, expire);
		while(!locked){
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//再次尝试竞争锁
			locked = this.getLock(jedis, key, expire);
		}
		return locked;
	}

	/**
	 * 释放redis锁，通知通知其他等待线程
	 * @author	 zc.ding
	 * @since 	 2017年4月25日
	 * @param key 锁的key
	 * @return
	 */
	public boolean freeLock(String key) {
		return freeLock(RedisFactory.getJedis(), key);
	}

	/**
	 * 释放redis锁，同时释放jedis连接，通知通知其他等待线程
	 * @author	 zc.ding
	 * @since 	 2017年4月25日
	 * @param jedis
	 * @param key
	 * @return
	 */
	public boolean freeLock(Jedis jedis, String key) {
		logger.debug(Thread.currentThread().getName() + " : 释放锁，通知等待线程");
		try {
			return jedis.del(key) > 0;
		} finally{
			close(jedis);

		}
	}

	/**
	 * 执行finally中逻辑操作
	 * @author	 zc.ding
	 * @since 	 2017年4月25日
	 * @param jedis
	 */
	public void close(Jedis jedis){
		if(jedis != null){
			jedis.close();
		}
	}

	/**
	 * 从redis获得含有有效时间的锁
	 * @author	 zc.ding
	 * @since 	 2017年4月25日
	 * @param key 锁的主键
	 * @param expire 过期时间
	 * @return
	 */
	private boolean getLock(String key, int expire){
		Jedis jedis = RedisFactory.getJedis();
		try {
			return getLock(jedis, key, expire);
		} finally{
			this.close(jedis);
		}
	}

	/**
	 * 从redis获得含有有效时间的锁
	 * @author	 zc.ding
	 * @since 	 2017年4月25日
	 * @param jedis
	 * @param key
	 * @param expire
	 * @return
	 */
	private boolean getLock(Jedis jedis, String key, int expire){
		if(jedis == null){
			return getLock(key, expire);
		}
		//失败:0, 成功:1
		long locked = jedis.setnx(key, String.valueOf(expire));
		if(locked == 1){
			jedis.expire(key, expire);
			return true;
		}
		return false;
	}

}
