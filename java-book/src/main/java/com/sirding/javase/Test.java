package com.sirding.javase;

import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

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
	
	@org.junit.Test
	public void test7(){
	    Map<String, String> map = new HashMap<>();
	    for(int i = 0; i < 100; i++){
	        if(i == 11 || i == 15 ){
                System.out.println("ok");
            }
	        map.put("你好" + i, "aa");
        }
    }
	
    @org.junit.Test
    public void test8(){
	    SortedMap<String, Object> map = new TreeMap<String, Object>();
	    map.put("aa", "hello");
	    map.put("bb", "world");
        SortedMap<String, Object> tailMap = ((TreeMap<String, Object>) map).tailMap("aa");
    }
    
    @org.junit.Test
    public void test9(){
	    Integer a = null;
	    Integer b = 100;
        System.out.println(Objects.equals(a, b));
    }
    
    @org.junit.Test
    public void test10(){
	    List<BigDecimal> list = Collections.emptyList();
        BigDecimal sum = BigDecimal.valueOf(list.stream().mapToDouble(o -> o.doubleValue()).sum());
        System.out.println(sum);
    }
    
    @org.junit.Test
    public void test11(){
//        String reg = "phpRedisAdmin/(.+\\.(jpg|gif|png|js|css]))";
        String reg = ".*[\\.](css|js)";
//        String reg = "/.*\\.(css|js)$";
        String msg = "phpRedisAdmin/css/index.css?v1-1-1";
        System.out.println(reg.matches(reg));
    }
    
    @org.junit.Test
    public void testInteger(){
	    Integer a1 = 1;
	    Integer a2 = 1;
        System.out.println("a1 == a2 : " + (a1 == a2));
	    Integer b1 = 128;
	    Integer b2 = 128;
        System.out.println("b1 == b2 : " + (b1 == b2));
	    int c1 = 500;
        Integer c2 = new Integer(500);
        System.out.println("c1 == c2 : " + (c1 == c2));

        Integer d1 = 1;
        Integer d2 = new Integer(1);
        System.out.println("d1 == d2 : " + (d1 == d2));
        Integer.valueOf(100);
        
    }
}
