package com.framework.redis.util;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.util.Pool;

/**
 * 
 * @author 	 zc.ding
 * @since 	 2017年5月15日
 * @version  1.1
 */
public class RedisClientUtils {
	private static final Log LOG = LogFactory.getLog(RedisClientUtils.class);
	private static Pool<Jedis> pool;

	static {
		init();
	}

	/**
	 * 获取资源
	 * 
	 * @return
	 */
	public static Jedis getResource() {
		if (pool == null) {
			throw new RuntimeException("Redis Client not init!");
		}
		return pool.getResource();
	}

	/**
	 * 关闭资源
	 * 
	 * @param jedis
	 */
	public static void closeResource(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	/**
	 * 从目录查找
	 * 
	 * @return
	 */
	private static Properties getPropByClassPath() {
		Properties prop = null;
		try {
			InputStream is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("/application_common_redis.properties");
			prop = new Properties();
			prop.load(is);
		} catch (Exception e) {
			LOG.error("can't find application_common_redis.properties file.", e);
			throw new RuntimeException("can't find application_common_redis.properties file.");
		}
		return prop;
	}

	/**
	 * 初始化
	 */
	public static synchronized void init() {
		try {
			Properties prop = getPropByClassPath();
			if (prop.containsKey("mode")) {
				if ("sentinel".equals(prop.getProperty("mode"))) {
					// 初始化哨兵模式
					initSentinelsPool(prop);
				} else if ("cluster".equals(prop.getProperty("mode"))) {
					// 初始化集群模式
					initClusterPool(prop);
				} else {
					// 自动检测
					autoDetect(prop);
				}
			} else {
				// 自动检测
				autoDetect(prop);
			}
		} catch (Exception e) {
			LOG.error("init redis pool fail", e);
			throw new RuntimeException("init redis pool fail", e);
		}
	}

	/**
	 * 自动检测,多个地址为哨兵模式，单个地址为一般连接
	 * 
	 * @param prop
	 */
	private static void autoDetect(Properties prop) {
		String[] hostAndPorts = getHostAndPorts(prop);
		if (hostAndPorts.length > 1) {
			initSentinelsPool(prop);
		} else {
			initCommonPool(prop);
		}

	}

	/**
	 * 获取主机与端口号列表
	 * 
	 * @param prop
	 * @return
	 */
	private static String[] getHostAndPorts(Properties prop) {
		return prop.getProperty("sentinels").trim().split(",");
	}

	/**
	 * 初始化哨兵池
	 * 
	 * @param prop
	 */
	private static void initSentinelsPool(Properties prop) {
		JedisPoolConfig config = initPoolConfig(prop);
		Set<String> sentinels = new HashSet<>(
				Arrays.asList(getHostAndPorts(prop)));
		pool = new JedisSentinelPool(prop.getProperty("masterName").trim(),
				sentinels, config, toInteger(prop, "timeout"));
	}

	/**
	 * 初始化集群池
	 * 
	 * @param prop
	 */
	private static void initClusterPool(Properties prop) {
		// wait to implement
		throw new RuntimeException("un implement cluster support!");
	}

	/**
	 * 初始化池配置
	 * 
	 * @param prop
	 * @return
	 */
	private static JedisPoolConfig initPoolConfig(Properties prop) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(toInteger(prop, "maxTotal"));
		config.setMaxIdle(toInteger(prop, "maxIdle"));
		config.setMaxWaitMillis(toInteger(prop, "maxWaitMillis"));
		config.setTestOnBorrow(toBoolean(prop, "testOnBorrow"));
		config.setTestOnReturn(toBoolean(prop, "testOnReturn"));
		return config;
	}

	/**
	 * 初始化哨兵池
	 * 
	 * @param prop
	 */
	private static void initCommonPool(Properties prop) {
		JedisPoolConfig config = initPoolConfig(prop);
		String[] hostAndPorts = getHostAndPorts(prop);
		String[] hostAndPort = hostAndPorts[0].split(":");
		String host = hostAndPort[0];
		int port = Integer.parseInt(hostAndPort[1]);
		pool = new JedisPool(config, host, port, toInteger(prop, "timeout"));

	}

	private static int toInteger(Properties prop, String key) {
		return Integer.parseInt(prop.getProperty(key).trim());
	}

	private static boolean toBoolean(Properties prop, String key) {
		return Boolean.valueOf(prop.getProperty(key).trim());
	}

	/**
	 * 模板方法
	 * 
	 * @param rc
	 * @return
	 */
	public static <T> T call(RedisCall<T> rc) {
		Jedis jedis = null;
		try {
			jedis = RedisClientUtils.getResource();
			return rc.run(jedis);
		} finally {
			if (jedis != null) {
				RedisClientUtils.closeResource(jedis);
			}
		}
	}
}
