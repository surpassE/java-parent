package com.sirding.javase.templatemethod;

import org.junit.Test;

public class TestCallTemplateMethod {

	/**
	 *  @Description    设计模式-模板方法
	 *  				抽象类(AbstractClass)|接口(interface) 定义模板方法，定义了业务或是算法的骨架或流程
	 *  				实现类	根据需求实现模板方法
	 *  @Method_Name    : test
	 *  @return         : void
	 *  @Creation Date  : 2017年11月1日 下午3:12:36 
	 *  @Author         : zhichaoding@hongkun.com zc.ding
	 */
	@Test
	public void test() {
		CallTemplateMethod callTemplateMethod = new CallTemplateMethod("I'm dobe!");
		callTemplateMethod.call((String tmp) ->{
			System.out.println(tmp);
			return tmp;
			});
	}
}
