package com.sirding.javaeight;

import java.util.*;
import java.util.function.Predicate;
import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

import org.junit.Test;

public class CollectionDemo {

	static Map<String, String> map = new HashMap<>();
	static Set<String> set = new HashSet<>();
	static List<String> list = new ArrayList<>();
	static String[] arr = {"a", "b", "c", "d"};
	
	static{
		map.put("name1", "zhic.ding");
		map.put("name2", "peng.wu.si");
		map.put("name3", "ping.zhong");
		map.put("name4", "guo.zhi");
		
		set.add("zc.ding");
		set.add("zhen.rong");
		set.add("peng.wu");
		set.add("ling.xiao");
		
		list.add("zc.ding");
		list.add("zhen.rong");
		list.add("peng.wu");
		list.add("ling.xiao");
	}
	
	@Test
	public void test1(){
		List<String> l = Arrays.asList(arr).stream().collect(toList());
		System.out.println(Arrays.stream(arr).anyMatch(Predicate.isEqual(null)));
		System.out.println(l.size());
		System.out.println("set中所有字符串的长度和 length:" + set.stream().mapToInt(String::length).sum());
		System.out.println(list.stream().filter(Objects::nonNull).distinct().collect(toMap(k -> k, v -> v)));;
		List<String> r = map.values().stream().filter(v -> "zhic.ding".equals(v)).collect(toList());
		System.out.println(r);
	}

	@Test
	public void test2(){
		List<Model> list = Arrays.asList(new Model("1", 1), new Model("3", 3), new Model("2", 2));
		list.sort((Model m1, Model m2) -> m1.getName().compareTo(m2.getName()));
//		排序择优方法
		list.sort(comparing(Model::getName));
		System.out.println(list);
	}





}
