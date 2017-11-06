package com.sirding.javase.classloader;

import org.junit.Test;

public class TestClassLoader {

	@Test
	public void test1() {
		ClassLoader classLoader = null;
		System.out.println(classLoader);
		System.out.println(TestClassLoader.class.getClassLoader().getResource(""));
	}
	
	@Test
	public void test2() throws Exception{
		Class<?> clazz = Class.forName("com.sirding.javase.classloader.ClassPojo");
		ClassLoader classLoader = clazz.getClassLoader();
		System.out.println("ClassLoader is " + classLoader.getClass().getSimpleName());
		while(classLoader.getParent() != null) {
			classLoader = classLoader.getParent();
			System.out.println("Parent is :" + classLoader.getClass().getSimpleName());
		}
	}
	
	@Test
	public void test3() throws Exception{
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		Class<?> clazz = classLoader.loadClass("com.sirding.javase.classloader.ClassPojo");
		System.out.println(clazz.getClassLoader().getClass().getSimpleName());
	}
	
	@Test
	public void test4() throws Exception {
	}
}
