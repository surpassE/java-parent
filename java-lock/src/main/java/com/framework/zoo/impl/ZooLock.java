package com.framework.zoo.impl;

import java.io.File;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import com.framework.zoo.Lock;
import com.framework.zoo.util.ZooCall;
import com.framework.zoo.util.ZooClientUtils;

public class ZooLock implements Lock{
	
	private String key;
	private static final String PREFIX = "NODE";

	public ZooLock(String key) {
		this.key = ZooClientUtils.ROOT_PATH + "/" + key + "/" + PREFIX;
	}
	
	@Override
	public boolean tryLock() {
		return ZooClientUtils.call(new ZooCall<Boolean>() {
			@Override
			public Boolean run(ZooKeeper zoo) {
				try {
					String result = zoo.create(
							key, 
							null, 
							ZooDefs.Ids.OPEN_ACL_UNSAFE, 
							CreateMode.EPHEMERAL_SEQUENTIAL);
					
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return true;
			}
		});
	}
	
	private boolean isMinNode(ZooKeeper zoo, String path){
//		zoo.getChildren(path, false);
		return false;
	}

	@Override
	public void unlock() {
		
	}

}
