package com.framework.redis.util;

import redis.clients.jedis.Jedis;

/**
 * 模板操作方法
 * @author 	 zc.ding
 * 
 * @version  1.1
 * @param <T>
 */
public interface RedisCall<T> {
	/**
	 * 操作
	 * 
	 * @param jedis
	 * @return
	 */
	public T run(Jedis jedis);
}
