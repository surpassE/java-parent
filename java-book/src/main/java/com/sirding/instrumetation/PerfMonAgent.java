package com.sirding.instrumetation;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PerfMonAgent {
	private static Instrumentation inst = null;
	static Logger logger = LogManager.getLogger(PerfMonAgent.class);
	
	public static void premain(String agentArgs, Instrumentation _inst){
		logger.debug("PerfMonAgent.premian() was called.");
		inst = _inst;
		ClassFileTransformer trans = new Perfmonxformer();
		logger.debug("Adding a PerfMonXformer instance to the JVM.");
		inst.addTransformer(trans);
	}
	
	public void test(){
//		
	}
}
