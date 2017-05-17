package com.sirding.spring.mvc;

import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator;

public class TestSpringMvc {

	static Logger logger = Logger.getLogger(TestSpringMvc.class);
	
	public static void main(String[] args) {
		ServletContextListener listener = null;
		XmlWebApplicationContext context = null;
		DispatcherServlet servlet = null;
		DefaultRequestToViewNameTranslator drtvnt = null;
		logger.debug(drtvnt);
		logger.debug(servlet);
		logger.debug(listener);
		logger.debug(context);
	}
}
