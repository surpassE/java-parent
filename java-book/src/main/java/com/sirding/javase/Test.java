package com.sirding.javase;

public class Test {

	/**
	 *  @Description    : ==
	 *  @Method_Name    : Test1
	 *  @return         : void
	 *  @Creation Date  : 2017年10月24日 下午10:37:27 
	 *  @Author         : zhichaoding@hongkun.com zc.ding
	 */
	@org.junit.Test
	public void Test1() {
		Integer a = 1;
		Integer b = new Integer(1);
		System.out.println("a == b : " + (a == b));
		String s1 = "hello";
		String s2 = new String("hello");
		System.out.println("s1 == s2 : " + (s1 == s2));
		
		Integer i = 200;
		Integer j = 200;
		
		System.out.println(i == j);
		
		int x = new Integer(200);
		int y = new Integer(200);
		System.out.println(x == y);
		
		int s3 = 200;
		Integer s4 = new Integer(200);
		System.out.println(s3 == s4);
	}
}
