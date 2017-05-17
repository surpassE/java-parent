package com.sirding.validate;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * @described	: 通过异常实现验证解耦 
 * @project		: com.sirding.validate.TestValidate
 * @author 		: zc.ding
 * @since 		: 2017年4月20日
 */
public class TestValidate {

	static Logger logger = LogManager.getLogger(TestValidate.class);
	ValidateService1 s1 = new ValidateService1();
	ValidateService2 s2 = new ValidateService2();
	
	/**
	 * @described			: 放在基类里
	 * @author				: zc.ding
	 * @project				: java-book
	 * @package				: com.sirding.validate.TestValidate.java
	 * @return				: Map<String,String>
	 * @since 				: 2017年4月20日
	 * @return
	 */
	Map<String, String> getResultMap(){
		Map<String, String> map = new HashMap<>();
		map.put("200", "success");
		return map;
	}
	
	@Test
	public void test() throws Exception{
		Map<String, String> map = getResultMap();
		try {
			s1.validate(map, "service1");
			s2.validate(map, "service1");
		} catch (ValidateFailException e) {
			logger.debug(e);
			int errCode = e.getErrCode();
			logger.debug(errCode);
		} 
	}
}
