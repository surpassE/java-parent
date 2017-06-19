package com.framework.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributeLock {
	/**
	 * key的前缀
	 * 
	 * @return
	 */
	String prefix() default "";
	
	/**
	 * 分布锁key
	 * 
	 * @return
	 */
	String key();

	/**
	 * key的后缀
	 * 
	 * @return
	 */
	String suffix() default "";
	
	/**
	 * key是否通过表达式进行取值，default是true
	 * 
	 * @return
	 */
	boolean expression() default true;
	
	/**
	 * 锁自动过期时间(秒),默认值10s
	 * 
	 * @return
	 */
	int expireTime() default 10;

	/**
	 * 最长等待时间(秒)<br/>
	 * 	-1：默认值，表示直到拿到锁为止
	 *  -2：执行lock(),尝试拿锁一次
	 * 
	 * @return
	 */
	int waitTime() default -1;

}
