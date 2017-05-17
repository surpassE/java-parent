package com.sirding.javase.compare;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class TestCompareInterface {

	static Logger logger = Logger.getLogger(TestCompareInterface.class);
	public static void main(String[] args) {
		MyImpl sub = new Sub();
		Class<?> c1 = MyImpl.class;
		Class<?> c2 = Parent.class;
		Class<?> c3 = Sub.class;
		
		logger.debug(c1.isInterface());
		logger.debug(c2.isInterface());
		logger.debug(c3.isInterface());
		logger.debug("测试isInstance...");
		logger.debug(c1.isInstance(sub));
		logger.debug(c2.isInstance(sub));
		logger.debug(c3.isInstance(sub));
		logger.debug(Pparent.class.isInstance(sub));
		logger.debug("测试isAssignableFrom......");
		logger.debug(c1.isAssignableFrom(MyImpl.class));
		logger.debug(c2.isAssignableFrom(Parent.class));
		logger.debug(c3.isAssignableFrom(Parent.class));
		logger.debug(c3.isAssignableFrom(MyImpl.class));
		logger.debug("=======");
		logger.debug(MyImpl.class.isAssignableFrom(Sub.class));
		logger.debug(Parent.class.isAssignableFrom(Sub.class));
		logger.debug(Pparent.class.isAssignableFrom(Sub.class));
		Map<String, String> map = new HashMap<>();
		for(Entry<String, String> entry : map.entrySet()){
			logger.debug(entry.getKey());
		}
	}
	
	/**
	 * @described			: 
	 * @author				: zc.ding
	 * @project				: java-book
	 * @package				: com.sirding.javase.compare.TestCompareInterface.java
	 * @return				: void
	 * @since 				: 2017年4月21日
	 */
	public void test(){	}
	
}
