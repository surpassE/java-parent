package com.framework.zoo.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import com.framework.zoo.ZooLockWatcher;

public class ZooClientUtils {

	private static final Log LOG = LogFactory.getLog(ZooClientUtils.class);
	
	public static final String ROOT_PATH = "/distribute_lock";
	private static ZooKeeper zooKeeper = null;
	
	static{
		initZookeeper();
	}
	
	private static void initZookeeper() {
		try {
//			Properties prop = getPropByClassPath();
//			String sessionTime = prop.getProperty("session_time");
//			String address = prop.getProperty("address");
			String sessionTime = "10000";
			String address = "127.0.0.1:2181";
			zooKeeper = new ZooKeeper(address, 
					Integer.parseInt(sessionTime), 
					new ZooLockWatcher());
			//创建根节点
			if(zooKeeper.exists(ROOT_PATH, true) == null){
				String result = zooKeeper.create(ROOT_PATH, 
						"".getBytes(), 
						ZooDefs.Ids.OPEN_ACL_UNSAFE, 
						CreateMode.PERSISTENT);
				LOG.info(ROOT_PATH + " create success. result:" + result);
			}
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException("fail to init zooKeeper.");
		}
	}
	
	/**
	 * @author	 zc.ding
	 * 
	 * @return
	 */
	private static Properties getPropByClassPath() {
		Properties prop = null;
		try {
			InputStream is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("/application_common_zoo.properties");
			prop = new Properties();
			prop.load(is);
		} catch (Exception e) {
			LOG.error("can't find application_common_zoo.properties file.", e);
			throw new RuntimeException("can't find application_common_zoo.properties file.");
		}
		return prop;
	}
	
	/**
	 * 模板方法
	 * 
	 * @param rc
	 * @return
	 */
	public static <T> T call(ZooCall<T> rc) {
		try {
			return rc.run(zooKeeper);
		} finally {
			//do soming
		}
	}
	
	public static void main(String[] args) throws Exception {
		for(int i = 0; i < 10; i++){
			String result = zooKeeper.create(
					ROOT_PATH + "/bid" + i, 
					null, 
					ZooDefs.Ids.OPEN_ACL_UNSAFE, 
					CreateMode.EPHEMERAL_SEQUENTIAL
					);
			LOG.debug(result);
		}
		
		List<String> list = zooKeeper.getChildren(ROOT_PATH , false);
		LOG.debug(list.size());
	}
}
