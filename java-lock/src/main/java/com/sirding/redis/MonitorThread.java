package com.sirding.redis;

import org.apache.log4j.Logger;

public class MonitorThread extends Thread{

	Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void run() {
		while(true){
			logger.debug("当前存活的线程:" + RedisFactory.getPool().getNumActive());
			logger.debug("当前空闲的线程:" + RedisFactory.getPool().getNumIdle());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
