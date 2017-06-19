package com.sirding.util;

import java.math.BigDecimal;

/**
 * 金额计算工具类
 * @author 	 zc.ding
 * @since 	 2017年5月2日
 * @version  1.1
 */
public class CalcUtil {

	private CalcUtil(){}
	
	/**
	 * num > 0
	 * @author	 zc.ding
	 * @since 	 2017年5月2日
	 * @param num
	 * @return
	 */
	public static boolean gtZero(BigDecimal num){
		return num.compareTo(BigDecimal.ZERO) > 0;
	}
	
	/**
	 * num >= 0
	 * @author	 zc.ding
	 * @since 	 2017年5月2日
	 * @param num
	 * @return
	 */
	public static boolean gteZero(BigDecimal num){
		return num.compareTo(BigDecimal.ZERO) >= 0;
	}
	
	/**
	 * num < 0
	 * @author	 zc.ding
	 * @since 	 2017年5月2日
	 * @param num
	 * @return
	 */
	public static boolean ltZero(BigDecimal num){
		return num.compareTo(BigDecimal.ZERO) < 0;
	}
	
	/**
	 * num <= 0
	 * @author	 zc.ding
	 * @since 	 2017年5月2日
	 * @param num
	 * @return
	 */
	public static boolean lteZero(BigDecimal num){
		return num.compareTo(BigDecimal.ZERO) <= 0;
	}
	
	/**
	 * num = 0
	 * @author	 zc.ding
	 * @since 	 2017年5月2日
	 * @param num
	 * @return
	 */
	public static boolean eZero(BigDecimal num){
		return num.compareTo(BigDecimal.ZERO) <= 0;
	}
	
	/**
	 * first > second
	 * @author	 zc.ding
	 * @since 	 2017年5月2日
	 * @param first
	 * @param second
	 * @return
	 */
	public static boolean gt(BigDecimal first, BigDecimal second){
		return first.compareTo(second) > 0;
	}
	
	/**
	 * first >= second
	 * @author	 zc.ding
	 * @since 	 2017年5月2日
	 * @param first
	 * @param second
	 * @return
	 */
	public static boolean gte(BigDecimal first, BigDecimal second){
		return first.compareTo(second) >= 0;
	}
	
	public static void main(String[] args) {
		BigDecimal first = new BigDecimal(1);
		BigDecimal second = new BigDecimal(2);
		System.out.println(gtZero(first));
		System.out.println(gtZero(first.multiply(BigDecimal.valueOf(-1))));
		System.out.println(gte(first, second));
	}
}
