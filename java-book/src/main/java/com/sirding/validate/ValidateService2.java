package com.sirding.validate;

import java.util.Map;

public class ValidateService2 {

	public void validate(Map<String, String> map, String data) throws ValidateFailException, Exception{
		if("service2".equals(data)){
			map.put("500", "我需要的参数是service2，你提供参数是" + data);
			throw new ValidateFailException(500);
		}
	}
}
