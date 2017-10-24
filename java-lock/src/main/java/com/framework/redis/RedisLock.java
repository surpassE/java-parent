package com.framework.redis;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.redis.Lock;
import com.framework.redis.util.RedisCall;
import com.framework.redis.util.RedisClientUtils;

import redis.clients.jedis.Jedis;


/**
 * 
 * @author 	 zc.ding
 * @since 	 2017年5月15日
 * @version  1.1
 */
public class RedisLock implements Lock {
	public static final int DEFAULT_EXPIRE_TIME = 10000;
	public static final long DEFAULT_TRY_INTERVAL = 50;
	public static final int DEFAULT_WAIT_TIME = 3600;
	private static final Log LOG = LogFactory.getLog(RedisClientUtils.class);
	private String key;
	private String val;
	/**
	 * 过期时间(秒)
	 */
	private int expire = DEFAULT_EXPIRE_TIME;
	/**
	 * 尝试间隔时间(秒)
	 */
	private long tryInterval = DEFAULT_TRY_INTERVAL;

	public RedisLock(String key) {
		this.key = key;
		this.val = UUID.randomUUID().toString();
	}

	public RedisLock(String key, int expire) {
		this.key = key;
		this.val = String.valueOf(expire);
		if (expire > 0) {
			this.expire = expire * 1000;
		}
	}

	public RedisLock(String key, int expire, long tryInterval) {
		this.key = key;
		this.val = UUID.randomUUID().toString();
		if (expire > 0) {
			this.expire = expire * 1000;
		}
		if (tryInterval > 0) {
			this.tryInterval = tryInterval;
		}
	}

	/**
	 * setNx 且设置超时时间
	 * 
	 * @param key
	 * @param val
	 * @param expireTime
	 * @return
	 */
	private boolean setNx(final String key, final String val, final int expireTime) {
		return RedisClientUtils.call(new RedisCall<Boolean>() {
			@Override
			public Boolean run(Jedis jedis) {
				if (jedis.setnx(key, val) == 1) {
					jedis.expire(key, expireTime / 1000);
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public boolean lock() {
		return RedisClientUtils.call(new RedisCall<Boolean>() {
			@Override
			public Boolean run(Jedis jedis) {
				if (setNx(key, val, expire)) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("get lock success ,key=" + key
								+ ", expire seconds=" + expire);
					}
					return true;
				} else {
					LOG.debug("get lock fail, key=" + key);
					return false;
				}
			}
		});

	}

	@Override
	public boolean tryLock(final int timeout) {
		long tryTime = System.currentTimeMillis() + timeout * 1000L;
		while (System.currentTimeMillis() < tryTime) {
			if (setNx(key, val, expire)) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("get lock success ,key=" + key
							+ ", expire seconds=" + expire);
				}
				return true;
			} else {
				LOG.debug("get lock fail, key=" + key);
			}
			try {
				TimeUnit.MILLISECONDS.sleep(tryInterval);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		return false;
	}
	
	/**
	 * 
	 */
	@Override
	public void unlock() {
		RedisClientUtils.call(new RedisCall<Boolean>() {
			@Override
			public Boolean run(Jedis jedis) {
//				jedis.eval(
//						"if redis.call('get',KEYS[1]) == ARGV[1] then \n return redis.call('del',KEYS[1]) \n else return 0 \n end",
//						Arrays.asList(key), Arrays.asList(val));
				return jedis.del(key) > 0;
			}
		});
	}
}
