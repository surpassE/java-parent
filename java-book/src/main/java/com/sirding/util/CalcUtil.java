package com.sirding.util;

import java.math.BigDecimal;

/**
*  金额计算工具类
*  @date                    ：2018/8/10
*  @author                  ：zc.ding@foxmail.com
*/
public interface CalcUtil {
	
	/**
	 * num > 0
	 * @author	 zc.ding
	 * @date 	 2017年5月2日
	 * @param num
	 * @return
	 */
	static boolean gtZero(BigDecimal num){
		return num.compareTo(BigDecimal.ZERO) > 0;
	}
	
	/**
	 * num >= 0
	 * @author	 zc.ding
	 * @since 	 2017年5月2日
	 * @param num
	 * @return
	 */
	static boolean gteZero(BigDecimal num){
		return num.compareTo(BigDecimal.ZERO) >= 0;
	}
	
	/**
	 * num < 0
	 * @author	 zc.ding
	 * @since 	 2017年5月2日
	 * @param num
	 * @return
	 */
	static boolean ltZero(BigDecimal num){
		return num.compareTo(BigDecimal.ZERO) < 0;
	}
	
	/**
	 * num <= 0
	 * @author	 zc.ding
	 * @since 	 2017年5月2日
	 * @param num
	 * @return
	 */
	static boolean lteZero(BigDecimal num){
		return num.compareTo(BigDecimal.ZERO) <= 0;
	}
	
	/**
	 * num = 0
	 * @author	 zc.ding
	 * @since 	 2017年5月2日
	 * @param num
	 * @return
	 */
	static boolean eZero(BigDecimal num){
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
	static boolean gt(BigDecimal first, BigDecimal second){
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
	static boolean gte(BigDecimal first, BigDecimal second){
		return first.compareTo(second) >= 0;
	}
	
	static void main(String[] args) {
		BigDecimal first = new BigDecimal(1);
		BigDecimal second = new BigDecimal(2);
		System.out.println(gtZero(first));
		System.out.println(gtZero(first.multiply(BigDecimal.valueOf(-1))));
		System.out.println(gte(first, second));
	}
}
