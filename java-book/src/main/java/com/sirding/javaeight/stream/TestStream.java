package com.sirding.javaeight.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static java.util.stream.Collectors.*;


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
}
