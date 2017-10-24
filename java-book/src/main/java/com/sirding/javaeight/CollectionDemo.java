package com.sirding.javaeight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		List<String> l = Arrays.asList(arr).stream().collect(Collectors.toList());
		System.out.println(Arrays.stream(arr).anyMatch(Predicate.isEqual(null)));
		System.out.println(l.size());
		System.out.println("set中所有字符串的长度和 length:" + set.stream().mapToInt(String::length).sum());
		System.out.println(list.stream().filter(Objects::nonNull).distinct().collect(Collectors.toMap(k -> k, v -> v)));;
		List<String> r = map.values().stream().filter(v -> "zhic.ding".equals(v)).collect(Collectors.toList());
		System.out.println(r);
	}
	
	
}
