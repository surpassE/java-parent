package com.sirding.redis;

import java.util.concurrent.atomic.AtomicInteger;

import redis.clients.jedis.Jedis;

/**
 * 获得redis分布式锁
 * @author 	 zc.ding
 * @since 	 2017年4月25日
 * @version  1.1
 */
public class RedisLockUtil {
	/**
	 * 是否有正在等待竞争锁线程
	 */
	private volatile boolean hasWaitThread = false;
	/**
	 * 持有锁的线程
	 */
	private volatile Object holdLockThread = new Object();
	/**
	 * 尝试获得锁的次数
	 */
	private AtomicInteger tryTimes = new AtomicInteger(0);
	/**
	 * 尝试获得锁的次数
	 */
	private final int MAX_TRY_TIMES = 1000;
	
	/**
	 * 获得redis锁
	 * @author	 zc.ding
	 * @since 	 2017年4月25日
	 * @param key 锁的主键
	 * @param expire 锁的过期时间
	 */
	public void tryLock(String key, int expire){
		this.tryLock(null, key, expire);
	}
	
	/**
	 * 获得redis锁
	 * @author	 zc.ding
	 * @since 	 2017年4月25日
	 * @param jedis redis连接
	 * @param key 锁的主键
	 * @param expire 锁的过期时间
	 */
	public void tryLock(Jedis jedis, String key, int expire){
		boolean locked = this.getLock(jedis, key, expire);
		while(!locked){
			//尝试执行wait()等待持有锁的线程进行唤醒
			tryWait(expire);
			//再次尝试竞争锁
			locked = this.getLock(jedis, key, expire);
			tryTimes.incrementAndGet();
		}
	}
	
	/**
	 * 释放redis锁，通知通知其他等待线程
	 * @author	 zc.ding
	 * @since 	 2017年4月25日
	 * @param key 锁的key
	 * @return
	 */
	public boolean freeLock(String key) {
		Jedis jedis = TestRedisLock.getJedis();
		try {
			return jedis.del(key) > 0;
		} finally{
			close(jedis);
			doReset();
		}
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
		try {
			return jedis.del(key) > 0;
		} finally{
			close(jedis);
			doReset();
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
		Jedis jedis = TestRedisLock.getJedis();
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
		//解决僵尸锁
		solveDeadLock(jedis, key, expire);
		if(locked == 1){
			//设置持有redis锁的线程&设置线程等待的标识
			this.holdLockThread = Thread.currentThread();
			this.hasWaitThread = true;
			this.resetTryTimes();
			jedis.expire(key, expire);
			return true;
		}
		return false;
	}
	
	/**
	 * 尝试让出竞争锁所占用的资源
	 * @author	 zc.ding
	 * @since 	 2017年4月28日
	 * @param expire
	 */
	private void tryWait(int expire){
		try {
			//已经有正在等待的线程&持有锁的线程不为空
			if(hasWaitThread &&  Runnable.class.isAssignableFrom(holdLockThread.getClass())){
				synchronized (holdLockThread) {
					if(hasWaitThread &&  Runnable.class.isAssignableFrom(holdLockThread.getClass())){
						//等待持有锁的线程执行notifyAll
						holdLockThread.wait(expire);
					}
				}
			}else{
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 解决用于redis服务器宕机发生的僵尸锁现象,关闭jedis
	 * @author	 zc.ding
	 * @since 	 2017年4月25日
	 * @param key
	 * @param expire
	 */
	private void solveDeadLock(String key, int expire) {
		Jedis jedis = TestRedisLock.getJedis();
		try {
			this.solveDeadLock(jedis, key, expire);
		} finally{
			this.close(jedis);
		}
	}
	
	/**
	 * 解决用于redis服务器宕机发生的僵尸锁现象
	 * @author	 zc.ding
	 * @since 	 2017年4月25日
	 * @param jedis redis连接
	 * @param key 锁的主键
	 * @param expire 过期时间
	 */
	private void solveDeadLock(Jedis jedis, String key, int expire){
		//判断尝试获得锁的次数
		if(tryTimes.get() < MAX_TRY_TIMES){
			return;
		}
		if(jedis == null){
			solveDeadLock(key, expire);
			return;
		}
		this.resetTryTimes();
		Long ttl = jedis.ttl(key);
		//锁的过期时间expire大于0 并且redis中的锁实际存储的过期时间是 -1 或是小于0，那么说明此锁是僵尸锁，直接删除
		if(ttl < 0 || expire > 0){
			jedis.del(key);
			doReset();
		}
	}
	
	/**
	 * 参数复原、唤醒等待的线程
	 * @author	 zc.ding
	 * @since 	 2017年4月25日
	 */
	private void doReset(){
		hasWaitThread = false;
		holdLockThread = new Object();
		//唤醒所有等待锁的线程
		synchronized (Thread.currentThread()) {
			System.out.println(Thread.currentThread().getName() + " : 释放锁，通知等待线程");
			Thread.currentThread().notifyAll();
		}
	}
	
	/**
	 * 重置计数器
	 * @author	 zc.ding
	 * @since 	 2017年4月26日
	 */
	private void resetTryTimes(){
		this.tryTimes.set(0);
	}
}
