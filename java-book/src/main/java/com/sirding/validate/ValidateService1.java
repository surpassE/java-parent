package com.sirding.validate;

import java.util.Map;

public class ValidateService1 {

	public void validate(Map<String, String> map, String data) throws ValidateFailException, Exception{
		if("service1".equals(data)){
			map.put("500", "我需要的参数是service1，你提供参数是" + data);
			throw new ValidateFailException(500);
		}
	}
}
