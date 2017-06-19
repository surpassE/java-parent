package com.sirding.javaeight;

/**
 * @described	: 
 * @project		: com.sirding.meet.Java8Demo
 * @author 		: zc.ding
 * @date 		: 2017年4月19日
 */
public interface Java8Interface {

	static void test(){
		System.out.println("1111");
	}
	
	default void test1() {
		System.out.println("222222");
	}
}
