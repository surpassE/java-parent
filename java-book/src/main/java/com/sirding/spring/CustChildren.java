package com.sirding.spring;

import org.apache.log4j.Logger;

public class CustChildren extends CustAbCls{

	final static Logger LOG = Logger.getLogger(CustChildren.class);
	@Override
	void init() {
		LOG.info("调用子类方法");
	}
}
