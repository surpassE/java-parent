package com.sirding.javase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @described	: 测试两个长字符串相加
 * @project		: com.sirding.javase.TestTwoLongStringAdd
 * @author 		: zc.ding
 * @date 		: 2017年4月19日
 */
public class TestTwoLongStringAdd {

	private TestTwoLongStringAdd(){}
	
	static final Logger LOGGER = LogManager.getLogger(TestTwoLongStringAdd.class);
	
	public static void main(String[] args) {
		String addend = "123111";
		String augend = "456";
		LOGGER.debug(addend + " + " + augend + " = " + strAdd("123111", "456"));
	}
	
	/**
	 * @Described			: 
	 * @author				: zc.ding
	 * @project				: java-book
	 * @package				: com.sirding.jvm.Demo10.java
	 * @return				: String
	 * @date 				: 2017年3月29日
	 * @param addend
	 * @param augend
	 * @return
	 */
	public static String strAdd(String addend, String augend){
		StringBuilder sb = new StringBuilder();
		StringBuilder addendTmp = new StringBuilder(addend).reverse();
		StringBuilder augendTmp = new StringBuilder(augend).reverse();
		
		if(addend.length() > augend.length()){
			for(int i = augend.length(); i < addend.length(); i++){
				augendTmp = augendTmp.append("0");
			}
		}
		if(augend.length() > addend.length()){
			for(int i = addend.length(); i < augend.length(); i++){
				
				addendTmp = addendTmp.append("0");
			}
		}
		LOGGER.debug(addend + "高位补0:" + augendTmp.toString());
		LOGGER.debug(augend + "高位补0:" + addendTmp.toString());
		
		boolean addOne = false;
		for(int i = 0; i < addend.length(); i++){
			int sum = Integer.parseInt(Character.toString(addendTmp.charAt(i))) + Integer.parseInt(Character.toString(augendTmp.charAt(i)));
			if(addOne){
				sum++;
			}
			if(sum >= 10){
				addOne = true;
				sum = sum - 10;
			}else{
				addOne = false;
			}
			sb.append(sum);
		}
		if(addOne){
			sb.append("1");
		}
		return sb.reverse().toString();
	}
}
