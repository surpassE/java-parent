package com.sirding.jdkproxy;

import java.io.FileOutputStream;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

public class TestJavassist {

	public static void main(String[] args) {
		ClassPool pool = ClassPool.getDefault();
		CtClass ctClass = pool.makeClass("com.sirding.jdkproxy.BaseModel");
		ctClass.stopPruning(true);
		    try {
		        //添加属性
		        ctClass.addField(CtField.make("private int age;", ctClass));
		        //添加setAge方法
		        ctClass.addMethod(CtMethod.make("public void setAge(int age){this.age = age;}", ctClass));
		        ctClass.addMethod(CtMethod.make("public int getAge(){return this.age;}", ctClass));

		        byte[] byteArray = ctClass.toBytecode();
		        FileOutputStream output = new FileOutputStream("/Users/luoxiaohui/Desktop/test/Test.class");
		        output.write(byteArray);                                        
		        output.close();
		        System.out.println("文件写入成功!!!");

		        if(ctClass.isFrozen()){
		            ctClass.defrost();
		        }
		        ctClass = pool.get("com.luoxiaohui.Test");
		        ctClass.addField(CtField.make("private String sex;", ctClass));
		        ctClass.addField(CtField.make("private String name;", ctClass));

//		        byteArray = ctClass.toBytecode();
//		        output = new FileOutputStream("/Users/luoxiaohui/Desktop/test/Test.class");
//		        output.write(byteArray);
//		        output.close();

		        System.out.println("文件修改成功!!!!");

		    } catch (Exception e) {
		        e.printStackTrace();
		    }	
	}
}
