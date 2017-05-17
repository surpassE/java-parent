package com.framework.redis.aop;

import java.lang.reflect.Method;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.framework.redis.Lock;
import com.framework.redis.annotation.DistributeLock;
import com.framework.redis.impl.RedisLock;

/**
 * 分布式锁切面
 * @author 	 zc.ding
 * 
 * @version  1.1
 */
@Component
@Aspect
public class DistributeLockAop {
	ScriptEngine engine = (new ScriptEngineManager()).getEngineByName("js");
	private static final Log LOG = LogFactory.getLog(DistributeLockAop.class);

	public DistributeLockAop() {
		if(LOG.isDebugEnabled()){
			LOG.debug("init DistributeLockAop");
		}
	}
	
	/**
	 * 环形切面
	 * @author	 zc.ding
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(com.framework.redis.annotation.DistributeLock)")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
		DistributeLock distributeLock = AnnotationUtils.findAnnotation(
				getTargetMethod(joinPoint), DistributeLock.class);
		if(distributeLock != null){
			String defKey = getDefKey(distributeLock, joinPoint.getArgs());
			Lock lock = new RedisLock(defKey, distributeLock.expireTime());
			try {
				if (distributeLock.waitTime() > 0 && 
						lock.tryLock(distributeLock.waitTime()) || 
						lock.lock()) {
					return joinPoint.proceed();
				} else {
					throw new RuntimeException("wait for lock timeout!");
				}
			} finally {
				lock.unlock();
			}
		}
		return joinPoint.proceed();
	}
	
	/**
	 * 获得目的方法
	 * @author	 zc.ding
	 * 
	 * @return
	 * @throws NoSuchMethodException
	 */
	private Method getTargetMethod(ProceedingJoinPoint joinPoint) throws NoSuchMethodException{
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		return ReflectUtils.findDeclaredMethod(
				joinPoint.getTarget().getClass(), 
				joinPoint.getSignature().getName(),
				methodSignature.getParameterTypes()
				);
	}
	
	/**
	 * 从注解注解中解析出key的值
	 * @author	 zc.ding
	 * @param distributeLock	分布式注解
	 * @param args	方法中参数
	 * @return
	 */
	private String getDefKey(DistributeLock distributeLock, Object[] args) {
		String key = distributeLock.key();
		String prefix = distributeLock.prefix();
		String suffix = distributeLock.suffix();
		if(!distributeLock.expression()){
			return prefix + key + suffix;
		}
		ScriptContext context = new SimpleScriptContext();
		context.setAttribute("args", args, ScriptContext.ENGINE_SCOPE);
		try {
			return prefix + (String) engine.eval(key, context) + suffix;
		} catch (ScriptException e) {
			if(LOG.isDebugEnabled()){
				LOG.debug("fail to eval express", e);
			}
			throw new RuntimeException("fail to eval express", e);
		}
	}
}
