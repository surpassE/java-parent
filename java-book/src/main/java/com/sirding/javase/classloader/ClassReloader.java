package com.sirding.javase.classloader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class ClassReloader extends ClassLoader{
	
	private String classPath;
	private String classname = "com.sirding.javase.classloader.ClassPojo";
	
	public ClassReloader(String classPath){
		this.classPath = classPath;
	}
	
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		ClassPojo cp = new ClassPojo();
		this.getClass().getClassLoader().loadClass(ClassPojo.class.getName());
		byte[] classData = getData(name);
		if(classData == null){
			throw new ClassNotFoundException();
		}else{
			return defineClass(classname, classData, 0, classData.length);
		}
	}
	
	public byte[] getData(String className){
		String path = classPath + className;
		try {
			InputStream is = new FileInputStream(path);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			byte[] buffer = new byte[2048];
			int num = 0;
			while((num = is.read(buffer)) != -1 ){
				stream.write(buffer, 0, num);
			}
			return stream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(ClassReloader.class.getResource(""));
			String path = "C:/yrtz/git/github/java-book/target/classes/com/sirding/javase/classloader/";
			ClassReloader loader1 = new ClassReloader(path);
			Class r = loader1.findClass("ClassPojo.class");
			System.out.println(r.newInstance());
			ClassReloader loader2 = new ClassReloader(path);
			Class r2 = loader2.findClass("ClassPojo.class");
			System.out.println(r2.newInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
