package com.sirding.zoo;

import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 
 * @author 	 zc.ding
 * @since 	 2017年5月9日
 * @version  1.1
 */
public class ZooFactory {

	private static String zoo_conn = "127.0.0.1:2181";
	private static CuratorFramework client = null;
	
	/**
	 * 与zookeeper建立连接
	 * @author	 zc.ding
	 * @since 	 2017年5月9日
	 */
	public static void init(){
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		client = CuratorFrameworkFactory.newClient(zoo_conn, retryPolicy);
		client.start();
	}
	
	/**
	 * 获得锁
	 * @author	 zc.ding
	 * @since 	 2017年5月9日
	 * @param path
	 * @param expire
	 * @return
	 * @throws Exception
	 */
	public static ZooLock tryLock(String path, long expire) throws Exception{
		InterProcessMutex lock = new InterProcessMutex(client, path);
		if(lock.acquire(expire, TimeUnit.SECONDS)){
			return new ZooLock(lock, true);
		}
		return new ZooLock(null, false);
	}
	
	/**
	 * 释放锁
	 * @author	 zc.ding
	 * @since 	 2017年5月9日
	 * @param zooLock
	 * @throws Exception
	 */
	public static void freeLock(ZooLock zooLock) throws Exception{
		zooLock.getLock().release();
	}
	
	/**
	 * 
	 * @author 	 zc.ding
	 * @since 	 2017年5月9日
	 * @version  1.1
	 */
	static class ZooLock {
		private InterProcessMutex lock = null;
		private boolean acquireFlag = true;

		public ZooLock(){}
		
		public ZooLock(InterProcessMutex lock, boolean acquireFlag){
			this.lock = lock;
			this.acquireFlag = acquireFlag;
		}
		
		public InterProcessMutex getLock() {
			return lock;
		}
		public void setLock(InterProcessMutex lock) {
			this.lock = lock;
		}

		public boolean isAcquireFlag() {
			return acquireFlag;
		}

		public void setAcquireFlag(boolean acquireFlag) {
			this.acquireFlag = acquireFlag;
		}
	}
}
