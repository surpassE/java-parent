package com.sirding.javase;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;

import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.StringUtils;

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
	
	@org.junit.Test
	public void test2() {
		Map<String, Object> map = new Hashtable<>();
		map.put("a", 1);
		map.put("b", 2);
		map.put("c", 4);
		map.keySet().forEach(key -> System.out.println(map.get(key)));
		
	}
	
	
	void foo() {
		String name = "ecplise";
		int count = name.length();
		System.out.println(name + count);
		bar(name);
	}
	
	void bar(String name) {
		
	}
	
	
	@org.junit.Test
	public void test3() {
		int a = 1;
		System.out.println(((Number)a instanceof Integer));
		Model m = new Model("aa");
		m.setAge(10);
		ReflectionUtils.doWithFields(m.getClass(), new FieldCallback() {
			@Override
			public void doWith(Field field) {
				System.out.println(field.getType().getName());
				if(field.getName().equals("age")) {
					try {
						field.setAccessible(true);
						field.set(m,  0);
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
		});
		System.out.println(m.getAge());
	}

	@org.junit.Test
	public void test4(){
		String msg = "aa,bb;cc \n\t cc,dd ggg";
		Arrays.stream(StringUtils.tokenizeToStringArray(msg, ",; \n\t")).forEach(e -> System.out.println(e));
	}
	
	@org.junit.Test
	public void test5() {
		String str = null;
		Optional<String> optional = Optional.of(str);
	}
	
	@org.junit.Test
	public void test6() {
		Integer num = 2311;
		System.out.println((num/100)%10);
	}
	
}
