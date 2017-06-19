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
	
	/**
	 * @Described			: 删除reids中key及对应的值
	 * @author				: zc.ding
	 * @date 				: 2016年11月29日
	 * @param keys			: keys 类型：byte[] | String | (byte[]、String组合) 
	 */
	public static void delKey(Object... keys){
		Jedis jedis = null;
		try {
			jedis = RedisClientUtils.getResource();
			if(keys != null){
				for(Object key : keys){
					//判断key的类型是不是byte[]类型
					if(key.getClass().equals(byte[].class.toString())){
						jedis.del((byte[])key);
					}
					//判断key的类型是不是String类型
					if(key.getClass().toString().equals(String.class.toString())){
						jedis.del((String)key);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (jedis != null) {
				RedisClientUtils.closeResource(jedis);
			}
		}
	}
	
	/**
	 * 
	 * @Described			: 向redis中添加属性值
	 * @author				: zc.ding
	 * @date 				: 2016年11月29日
	 * @param key			:
	 * @param value			:
	 */
	public static void addValue(String key, String value){
		Jedis jedis = null;
		try {
			jedis = RedisClientUtils.getResource();
			jedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (jedis != null) {
				RedisClientUtils.closeResource(jedis);
			}
		}
	}

	/**
	 * @Described			: 向redis中添加指定有效期的属性值
	 * @author				: zc.ding
	 * @date 				: 2017年1月3日
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public static void addValue(String key, String value, int seconds){
		Jedis jedis = null;
		try {
			jedis = RedisClientUtils.getResource();
			jedis.setex(key, seconds, value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (jedis != null) {
				RedisClientUtils.closeResource(jedis);
			}
		}
	}
	
	/**
	 * @Described			: 从redis中获得指定key对应的数据信息
	 * @author				: zc.ding
	 * @date 				: 2016年11月29日
	 * @param key
	 * @return
	 */
	public static String getValue(String key){
		if(key == null){
			return null;
		}
		Jedis jedis = null;
		String value = null;
		try {
			jedis = RedisClientUtils.getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (jedis != null) {
				RedisClientUtils.closeResource(jedis);
			}
		}
		return value;
	}
	
	/**
	 * @Described			: 向redis中添加对象的属性值
	 * @author				: zc.ding
	 * @date 				: 2016年12月28日
	 * @param key
	 * @param obj
	 */
//	public static void addObject(String key, Object obj){
//		Jedis jedis = null;
//		boolean broken = false;
//		try {
//			jedis = RedisClientUtils.getResource();
//			jedis.set(key.getBytes(), SerializeUtil.serializeKryo(obj));
//		} catch (Exception e) {
//			e.printStackTrace();
//			broken = true;
//		}finally {
//			if (jedis != null) {
//				RedisClientUtils.closeResource(jedis);
//			}
//		}
//	}
	
	/**
	 * @Described			: 向redis添加指定有效期的对象属性值
	 * @author				: zc.ding
	 * @date 				: 2017年1月3日
	 * @param key
	 * @param obj
	 * @param seconds
	 */
//	public static void addObject(String key, Object obj, int seconds){
//		Jedis jedis = null;
//		boolean broken = false;
//		try {
//			jedis = RedisClientUtils.getResource();
//			jedis.setex(key.getBytes(), seconds, SerializeUtil.serializeKryo(obj));
//		} catch (Exception e) {
//			e.printStackTrace();
//			broken = true;
//		}finally {
//			if (jedis != null) {
//				RedisClientUtils.closeResource(jedis);
//			}
//		}
//	}
	
	/**
	 * 加载对象信息
	 * @author	 zc.ding
	 * @since 	 2017年5月21日
	 * @param key
	 * @param clazz
	 * @return
	 */
//	public static <T> T getObject(String key, Class<T> clazz){
//		Jedis jedis = null;
//		byte[] buff = null;
//		if(key == null){
//			return null;
//		}
//		boolean broken = false;
//		try {
//			jedis = RedisClientUtils.getResource();
//			buff = jedis.get(key.getBytes());
//		} catch (Exception e) {
//			e.printStackTrace();
//			broken = true;
//		}finally{
//			if (jedis != null) {
//				RedisClientUtils.closeResource(jedis);
//			}
//		}
//		return SerializeUtil.unSerializeKryo(buff, clazz);
//	}
	
	/**
	 * @Described			: 清空redis库
	 * @author				: zc.ding
	 * @date 				: 2017年1月3日
	 * @return
	 */
	public String flushAll() {
		Jedis jedis = RedisClientUtils.getResource();
		String stata = jedis.flushAll();
		RedisClientUtils.closeResource(jedis);
		return stata;
	}
	
	/**
	 * @Described			: 设置key的有效时间
	 * @author				: zc.ding
	 * @date 				: 2017年1月3日
	 * @param key
	 * @param seconds
	 * @return
	 */
	public static long expired(String key, int seconds) {
		Jedis jedis = RedisClientUtils.getResource();
		long count = jedis.expire(key, seconds);
		RedisClientUtils.closeResource(jedis);
		return count;
	}
	
	/**
	 * @Described			: 设置key有效截止时间
	 * @author				: zc.ding
	 * @date 				: 2017年1月3日
	 * @param key
	 * @param timestamp
	 * @return
	 */
	public long expireAt(String key, long timestamp) {
		Jedis jedis = RedisClientUtils.getResource();
		long count = jedis.expireAt(key, timestamp);
		RedisClientUtils.closeResource(jedis);
		return count;
	}
	
	/**
	 * @Described			: 取消key的有效时间配置
	 * @author				: zc.ding
	 * @date 				: 2017年1月3日
	 * @param key
	 * @return
	 */
	public long persist(String key) {
		Jedis jedis = RedisClientUtils.getResource();
		long count = jedis.persist(key);
		RedisClientUtils.closeResource(jedis);
		return count;
	}
}
