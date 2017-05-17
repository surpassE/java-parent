package com.sirding.javase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestLog4j2 {

	static Logger logger = LogManager.getLogger(TestLog4j2.class);
	
	public static void main(String[] args) {
		int i = 100;
		while(i > 0){
			logger.trace("测试日志的trace级别信息......");
			logger.debug("测试日志的debug级别信息......");
			logger.info("测试日志的info级别信息......");
			logger.warn("测试日志的warn级别信息......");
			logger.error("测试日志的error级别信息......");
			logger.fatal("测试日志的fatal级别信息......");
			
			System.out.println("okok......");
			i--;
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
