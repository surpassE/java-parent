package com.sirding.javase.templatemethod;

/**
 * @Description   : 调用模板方法
 * @Project       : java-book
 * @Program Name  : com.sirding.javase.templatemethod.CallTemplateMethod.java
 * @Author        : zhichaoding@hongkun.com zc.ding
 */
public class CallTemplateMethod {

	private String param = "Hello";

	public CallTemplateMethod() {}
	
	public CallTemplateMethod(String param) {
		this.param = param;
	}
	
	public <T> T call(TemplateMethodI<T> templateMethodI) {
		return templateMethodI.call(param);
	}
}
