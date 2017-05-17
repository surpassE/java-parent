package com.sirding.spring;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

public class SpringJms {

	Logger logger = Logger.getLogger(getClass());
	public void test(){
		JmsTemplate jmsTemplate = new JmsTemplate();
		logger.debug(jmsTemplate);
		jmsTemplate.send(null);
		jmsTemplate.receive();
		DefaultMessageListenerContainer container = null;
		logger.debug(container);
	}
}
