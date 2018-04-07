package com.sirding.javase;

/**
 * @Description   : 测试eplise快捷键
 * @Project       : java-book
 * @Program Name  : com.sirding.javase.TestKeyMap.java
 * @Author        : zhichaoding@hongkun.com zc.ding
 */
public class TestKeyMap {

	/**
	 * alt+shift+A 模块修改
	 * int num1;		public static long num1;
	 * int num2;    ->	public static long num2;
	 * int num3;		public static long num3;
	 */
	public static long num1;
	public static long num2;
	public static long num3;
	
	
	/**
	 *  @Description    : 通过systrace直接输出System.out.println("TestKeyMap.t()");RadonDB
	 *  @Method_Name    : t
	 *  @return         : void
	 *  @Creation Date  : 2018年1月31日 下午3:34:29 
	 *  @Author         : zhichaoding@hongkun.com zc.ding
	 */
	public void a() {
		System.out.println("TestKeyMap.t()");
	}
	
	
	/**
	 *  @Description    : 用if-eles替换三元运算符，选中result 按 ctrl+1 选择 Replace conditional with if-else查看效果
	 *  @Method_Name    : b
	 *  @param param
	 *  @return         : void
	 *  @Creation Date  : 2018年2月1日 上午9:19:27 
	 *  @Author         : zhichaoding@hongkun.com zc.ding
	 */
	public void b(String param) {
//		int result = "1".equals(param) ? 1 : 0;
		int result;
		if ("1".equals(param))
			result = 1;
		else
			result = 0;
		System.out.println(result);
	}
	
	
	
	/** 
	 *  @Description    : alt + shift + j 添加javadoc
	 *  @Method_Name    : c
	 *  @param 			: p1
	 *  @param 			: p2
	 *  @return         : void
	 *  @Creation Date  : 2018年2月5日 上午9:11:13 
	 *  @Author         : zhichaoding@hongkun.com zc.ding
	 */
	    
	public void c(String p1, Integer p2) {
		
		return;
	}
	
}
