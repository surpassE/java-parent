package com.sirding.web;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/lockController")
public class LockController {

	private static final Logger LOG = Logger.getLogger(LockController.class);
	
	
	/**
	 * 通过注解的方式使用redis锁<br/>
	 * <b>友情推荐</b>
	 * @author	 zc.ding
	 * 
	 * @param key 主键
	 * @return
	 */
	@RequestMapping(params = "testLock")
	@ResponseBody
//	@DistributeLock(key = "args[0]", expireTime = 100, waitTime = 3600)
	public Map<String, String> testLock(String key){
		Map<String, String> map = new HashMap<>();
		LOG.debug("主键：" + key);
		LOG.debug("do something......");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			LOG.debug("sleep interrupted", e);
			LOG.debug(e.getMessage());
		}
		map.put("suc", "suc");
		return map;
	}
	
	/**
	 * 直接使用redis锁
	 * @author	 zc.ding
	 * 
	 * @param key 主键
	 * @return
	 */
	@RequestMapping(params = "testLock2")
	@ResponseBody
	public Map<String, String> testLock2(String key){
		Map<String, String> map = new HashMap<>();
//		Lock lock = new RedisLock(key, 100);
//		try {
//			if(lock.tryLock(10000)){
//				LOG.debug("主键：" + key);
//				LOG.debug("do something......");
//				Thread.sleep(1000);
//			}
//		} catch (Exception e) {
//			LOG.debug("sleep interrupted", e);
//			LOG.debug(e.getMessage());
//		}finally {
//			lock.unlock();
//		}
//		map.put("suc", "suc");
		return map;
	}
	
	@RequestMapping(params = "testLock3")
	@ResponseBody
	public Map<String, String> testLock3(String key){
		Map<String, String> map = new HashMap<>();
//		Lock lock = new RedisLock(key, 100);
//		try {
//			if(lock.tryLock(10000)){
//				LOG.debug("主键：" + key);
//				LOG.debug("do something......");
//				Thread.sleep(1000);
//			}
//		} catch (Exception e) {
//			LOG.debug("sleep interrupted", e);
//			LOG.debug(e.getMessage());
//		}finally {
//			lock.unlock();
//		}
//		map.put("suc", "suc");
		return map;
	}
}
