package com.sirding.spring;

import org.apache.log4j.Logger;

public abstract class CustAbCls {
	final static Logger LOG = Logger.getLogger(CustAbCls.class);
	CustAbCls(){
		LOG.info("初始化基类...");
		this.init();
	}
	
	abstract void init();

}
