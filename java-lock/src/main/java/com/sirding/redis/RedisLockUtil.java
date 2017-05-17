package com.sirding.redis;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

/**
 * 获得redis分布式锁
 * @author 	 zc.ding
 * @since 	 2017年4月25日
 * @version  1.1
 */
public class RedisLockUtil {
	Logger logger = Logger.getLogger(getClass());
	
	/**
	 * 
	 */
	private Map<String, InnerRedisLock> lockMap = new ConcurrentHashMap<String, InnerRedisLock>();
	
	/**
	 * 
	 */
	private static final Object LOCK = new Object();
	
	/**
	 * 
	 * @author	 zc.ding
	 * @since 	 2017年5月9日
	 * @param key
	 * @param expire
	 * @return
	 */
	public boolean tryLock(String key, int expire){
		InnerRedisLock innerRedisLock = lockMap.get(key);
		if(!lockMap.containsKey(key) || lockMap.get(key) == null){
			synchronized (LOCK) {
				logger.debug("==============竞争锁===================");
				innerRedisLock = lockMap.get(key);
				if(!lockMap.containsKey(key) || lockMap.get(key) == null){
					innerRedisLock = new InnerRedisLock();
					lockMap.put(key, innerRedisLock);
				}
			}
		}
		return innerRedisLock.tryLock(key, expire);
	}
	
	/**
	 * 释放redis锁，通知通知其他等待线程
	 * @author	 zc.ding
	 * @since 	 2017年4月25日
	 * @param key 锁的key
	 * @return
	 */
	public boolean freeLock(String key) {
		InnerRedisLock innerRedisLock = lockMap.get(key);
		boolean flag = innerRedisLock.freeLock(key);
		if(innerRedisLock.getThreadCounts().intValue() == 0){
			lockMap.remove(key);
		}
		return flag;
	}
	
	public int getSize(){
		return lockMap.size();
	}
	
	/**
	 * 
	 * @author 	 zc.ding
	 * @since 	 2017年5月9日
	 * @version  1.1
	 */
	static class InnerRedisLock{
		Logger logger = Logger.getLogger(getClass());
		/**
		 * 是否有正在等待竞争锁线程
		 */
//		private volatile boolean hasWaitThread = false;
		/**
		 * 持有锁的线程
		 */
		private volatile Object holdLockThread = new Object();
		/**
		 * 尝试获得锁的次数
		 */
		private AtomicInteger tryTimes = new AtomicInteger(0);
		/**
		 * 竞争锁的线程数量
		 */
		private AtomicInteger threadCounts = new AtomicInteger(0);
		/**
		 * 尝试获得锁的次数
		 */
		private final int MAX_TRY_TIMES = 1000;
		
		/**
		 * 获得redis锁
		 * @author	 zc.ding
		 * @since 	 
		 * @param key 锁的主键
		 * @param expire 锁的过期时间
		 * @return
		 */
		public boolean tryLock(String key, int expire){
			threadCounts.incrementAndGet();
			boolean locked = this.getLock(key, expire);
			while(!locked){
				//尝试执行wait()等待持有锁的线程进行唤醒
				tryWait(expire);
				//再次尝试竞争锁
				locked = this.getLock(key, expire);
			}
			return locked;
		}
		
		/**
		 * 释放redis锁，通知通知其他等待线程
		 * @author	 zc.ding
		 * @since 	 
		 * @param key 锁的key
		 * @return
		 */
		public boolean freeLock(String key) {
			Jedis jedis = RedisFactory.getJedis();
			logger.debug(Thread.currentThread().getName() + " : 释放锁，通知等待线程");
			try {
				return jedis.del(key) > 0;
			} finally{
				threadCounts.decrementAndGet();
				close(jedis);
				doReset();
			}
		}
		
		/**
		 * 执行finally中逻辑操作
		 * @author	 zc.ding
		 * @since 	 
		 * @param jedis
		 */
		public void close(Jedis jedis){
			if(jedis != null){
				jedis.close();
			}
		}
		
		/**
		 * 
		 * @author	 zc.ding
		 * @since 	 2017年5月9日
		 * @return
		 */
		public AtomicInteger getThreadCounts(){
			return this.threadCounts;
		}
		
		/**
		 * 从redis获得含有有效时间的锁
		 * @author	 zc.ding
		 * @since 	 
		 * @param key 锁的主键
		 * @param expire 过期时间
		 * @return
		 */
		private boolean getLock(String key, int expire){
			Jedis jedis = RedisFactory.getJedis();
			try {
				//失败:0, 成功:1
				if(jedis.setnx(key, String.valueOf(expire)) == 1){
					//设置持有redis锁的线程&设置线程等待的标识
					this.holdLockThread = Thread.currentThread();
					this.resetTryTimes();
					tryTimes.incrementAndGet();
					jedis.expire(key, expire);
					return true;
				}
				//解决僵尸锁
				solveDeadLock(key, expire);
				return false;
			} finally{
				this.close(jedis);
			}
		}
		
		/**
		 * 尝试让出竞争锁所占用的资源
		 * @author	 zc.ding
		 * @since 	 
		 * @param expire
		 */
		private void tryWait(int expire){
			try {
				//持有锁的线程不为空
				if(Runnable.class.isAssignableFrom(holdLockThread.getClass())){
					synchronized (holdLockThread) {
						if(Runnable.class.isAssignableFrom(holdLockThread.getClass())){
							//等待持有锁的线程执行notifyAll
							holdLockThread.wait(expire);
						}
					}
				}else{
					Thread.sleep(5);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * 解决用于redis服务器宕机发生的僵尸锁现象,关闭jedis
		 * @author	 zc.ding
		 * @since 	 
		 * @param key
		 * @param expire
		 */
		private void solveDeadLock(String key, int expire) {
			//判断尝试获得锁的次数
			if(tryTimes.get() < MAX_TRY_TIMES){
				return;
			}
			this.resetTryTimes();
			Jedis jedis = RedisFactory.getJedis();
			try {
				Long ttl = jedis.ttl(key);
				//锁的过期时间expire大于0 并且redis中的锁实际存储的过期时间是 -1 或是小于0，那么说明此锁是僵尸锁，直接删除
				if(ttl < 0 && expire > 0){
					jedis.del(key);
				}
			} finally{
				this.close(jedis);
			}
		}
		
		/**
		 * 参数复原、唤醒等待的线程
		 * @author	 zc.ding
		 * @since 	 
		 */
		private void doReset(){
			//唤醒所有等待锁的线程
			synchronized (Thread.currentThread()) {
				holdLockThread = new Object();
				Thread.currentThread().notifyAll();
			}
		}
		
		/**
		 * 重置计数器
		 * @author	 zc.ding
		 * @since 	 
		 */
		private void resetTryTimes(){
			this.tryTimes.set(0);
		}
	}
}
