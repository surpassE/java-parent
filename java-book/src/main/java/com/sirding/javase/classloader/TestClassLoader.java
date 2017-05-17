package com.sirding.javase.classloader;

public class TestClassLoader {

	public static void main(String[] args) {
		ClassLoader classLoader = null;
		System.out.println(classLoader);
		System.out.println(TestClassLoader.class.getClassLoader().getResource(""));
	}
}
