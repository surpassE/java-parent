package com.sirding.instrumetation;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.NotFoundException;

public class Perfmonxformer implements ClassFileTransformer{

	Logger logger = LogManager.getLogger(Perfmonxformer.class);
	
	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		byte[] transformed = null;
		logger.debug("Transforming " + className);
		ClassPool pool = ClassPool.getDefault();
		CtClass cl = null;
		try {
			cl = pool.makeClass(new ByteArrayInputStream(classfileBuffer));
			if(cl.isInterface() == false) {
				CtBehavior[] methods = cl.getDeclaredBehaviors();
				for(int i = 0; i < methods.length; i++){
					if(methods[i].isEmpty() == false){
						doMethod(methods[i]);
					}
				}
				transformed = cl.toBytecode();
			}
		} catch (Exception e) {
			logger.error(e);
			logger.error(e.getMessage());
		}finally {
			if(cl != null){
				cl.detach();
			}
		}
		return transformed;
	}
	
	private void doMethod(CtBehavior method) throws NotFoundException, CannotCompileException {
		method.insertBefore("long startTime = System.currentTimeMillis();");
		method.insertAfter("System.out.println(\"leave " + method.getName() + " and tim is :\" + System.currentTimeMillis() - startTime);");
		
	}
}
