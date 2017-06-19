package com.sirding.util;

/**
 * @Described	: IP操作工具类<br>
 * 					1:IP与Long型数字相互转换工具<br/>
 * @project		: com.sirding.util.IpUtil
 * @author 		: zc.ding
 * @date 		: 2016年12月15日
 */
public class IpUtil {
	
	/**
	 * @Described			: IP转为Long型数字
	 * @author				: zc.ding
	 * @date 				: 2016年12月15日
	 * @param ipAddress
	 * @return
	 */
	public static long ipToLong(String ip) {
		String[] ipAddressInArray = ip.split("\\.");
		long result = 0;
		for (int i = 0; i < ipAddressInArray.length; i++) {
			int power = 3 - i;
			int num = Integer.parseInt(ipAddressInArray[i]);
			// 1. 192 * 256^3
			// 2. 168 * 256^2
			// 3. 1 * 256^1
			// 4. 2 * 256^0
			result += num * Math.pow(256, power);
		}
		return result;
	}

	/**
	 * @Described			: Long型数字转为IP字符串
	 * @author				: zc.ding
	 * @date 				: 2016年12月15日
	 * @param num
	 * @return
	 */
	public static String longToIp(long num) {
		return ((num >> 24) & 0xFF) +
                   "." + ((num >> 16) & 0xFF) +
                   "." + ((num >> 8) & 0xFF) +
                   "." + (num & 0xFF);
	}

	/**
	 * @Described			: IP转为Long型数字
	 * @author				: zc.ding
	 * @date 				: 2016年12月15日
	 * @param ipAddress
	 * @return
	 */
	public static long ipToLong2(String ip) {
		long result = 0;
		String[] ipAddressInArray = ip.split("\\.");
		for (int i = 3; i >= 0; i--) {
			long num = Long.parseLong(ipAddressInArray[3 - i]);
			// left shifting 24,16,8,0 and bitwise OR
			// 1. 192 << 24
			// 1. 168 << 16
			// 1. 1 << 8
			// 1. 2 << 0
			result |= num << (i * 8);
		}
		return result;
	}
	
	/**
	 * @Described			: Long型数字转为IP字符串
	 * @author				: zc.ding
	 * @date 				: 2016年12月15日
	 * @param ip
	 * @return
	 */
	public static String longToIp2(long num) {
		StringBuilder sb = new StringBuilder(15);
		for (int i = 0; i < 4; i++) {
			// 1. 2
			// 2. 1
			// 3. 168
			// 4. 192
			sb.insert(0, Long.toString(num & 0xff));
			if (i < 3) {
				sb.insert(0, '.');
			}
			// 1. 192.168.1.2
			// 2. 192.168.1
			// 3. 192.168
			// 4. 192
			num = num >> 8;
		}
		return sb.toString();
	}
}