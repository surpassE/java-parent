package com.sirding.javaeight.stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;


public class TestStream {

	static Logger logger = LogManager.getLogger(TestStream.class);
	@Test
	public void test1() {
		List<String> words = Arrays.asList("Java 8", "Lambdas", "In", "Action");
		List<Integer> worldLengths = words.stream().map(String::length).collect(toList());
		logger.info("{}", worldLengths);
	}
	
	
	@Test
	public void test2() {
		IntStream is = IntStream.rangeClosed(1, 100);
		logger.info(is.sum());
	}
	
	@Test
	public void test3(){
        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>(); 
        map.merge("ok", 1L, Long::sum);
        Long result = map.search(Integer.MAX_VALUE, (k, v) -> {
            if("ok".equalsIgnoreCase(k)){
                return v;
            } 
            return 0L;
        });
        System.out.println(result);
        
    }
    
}
