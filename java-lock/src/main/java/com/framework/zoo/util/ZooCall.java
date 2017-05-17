package com.framework.zoo.util;

import org.apache.zookeeper.ZooKeeper;

public interface ZooCall<T> {

	/**
	 * 操作
	 * 
	 * @param jedis
	 * @return
	 */
	public T run(ZooKeeper zoo);
}
