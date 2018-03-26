package com.manager.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.manager.pojo.RedisData;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @ClassName : RedisUtil
 * @Description : redis工具类
 * @Date : 2018年3月22日 下午2:21:13
 * @Version : V1.0
 */
public class RedisUtil {
	private static JedisPool jedisPool;
	private static Jedis jedis;

	/**
	 * @Title : getConfiguration
	 * @Description : 获取配置文件信息
	 * @Date : 2018年3月22日 下午4:11:56
	 * @return
	 */
	private static Properties getConfiguration() {
		Properties properties = new Properties();
		try {
			// 使用ClassLoader加载properties配置文件生成对应的输入流
			InputStream in = RedisUtil.class.getClassLoader().getResourceAsStream("redisInfo.properties");
			// 使用properties对象加载输入流
			properties.load(in);
		} catch (Exception e) {
			e.getStackTrace();
		}

		return properties;
	}

	/**
	 * @Title : initRedisPool
	 * @Description : 建立连接池
	 * @Date : 2018年3月22日 下午3:32:52
	 * @return
	 */
	private static void initRedisPool() {
		// 获取配置信息
		Properties properties = getConfiguration();
		// 建立连接池配置参数
		JedisPoolConfig config = new JedisPoolConfig();
		// 设置最大活跃连接数
		config.setMaxTotal(Integer.parseInt(properties.getProperty("maxTotal")));
		// 设置最大阻塞时间
		config.setMaxWaitMillis(Long.valueOf(properties.getProperty("maxWaitMillis")));
		// 设置最大空闲连接数
		config.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle")));
		// 设置最小空闲连接数
		config.setMinIdle(Integer.parseInt(properties.getProperty("minIdle")));
		// 创建连接池
		jedisPool = new JedisPool(config, properties.getProperty("ip"),
				Integer.parseInt(properties.getProperty("port")));
	}

	/**
	 * @Title : getJedis
	 * @Description : 获取一个jedis对象
	 * @Date : 2018年3月22日 下午3:33:39
	 * @param args
	 */
	private static Jedis getJedis() {
		// 获取配置信息
		Properties properties = getConfiguration();
		// 创建连接池
		initRedisPool();
		// 获取jedis对象
		Jedis jedis = jedisPool.getResource();
		// 连接密码
		jedis.auth(properties.getProperty("passWord"));
		// 选择分区(0-15)
		jedis.select(Integer.parseInt(properties.getProperty("database")));

		return jedis;
	}

	/**
	 * @Title : returnRes
	 * @Description : 归还连接
	 * @Date : 2018年3月22日 下午3:39:50
	 * @param jedis
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	private static void returnRes(Jedis jedis) {
		jedisPool.returnResource(jedis);
	}

	/**
	 * @Title : setRedisData
	 * @Description : 存数据到redis
	 * @Date : 2018年3月22日 下午3:44:46
	 * @param key
	 * @param value
	 */
	public static void setRedisData(String key, String value) {
		try {
			// 获取jedis对象
			if (null == jedis) {
				jedis = getJedis();
			}
			// 存数据
			jedis.set(key, value);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	/**
	 * @Title : getRedisData
	 * @Description : 获取redis数据
	 * @Date : 2018年3月22日 下午3:49:15
	 * @param key
	 * @return
	 */
	public static String getRedisData(String key) {
		String value = null;

		try {
			// 获取jedis对象
			if (null == jedis) {
				jedis = getJedis();
			}
			// 获取数据
			value = jedis.get(key);
		} catch (Exception e) {
			e.getStackTrace();
		}

		return value;
	}

	/**
	 * @Title : delRedisData
	 * @Description : 删除指定key
	 * @Date : 2018年3月23日 上午9:25:22
	 * @param key
	 */
	public static void delRedisData(String key) {
		try {
			// 获取jedis对象
			if (null == jedis) {
				jedis = getJedis();
			}
			// 删除数据
			jedis.del(key);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	/**
	 * @Title : getRedisDataByPattern
	 * @Description : 获取指定前缀key的所有数据
	 * @Date : 2018年3月23日 上午9:36:49
	 * @param pattern
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<RedisData> getRedisDataByPattern(String pattern) {
		List<RedisData> results = new ArrayList<>();
		String key = null;
		String value = null;

		try {
			// 获取jedis对象
			if (null == jedis) {
				jedis = getJedis();
			}
			// 获取指定前缀的所有key
			Set<String> keys = jedis.keys(pattern + "*");
			// 根据key获取数据放入list
			for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
				key = (String) iterator.next();
				value = jedis.get(key);
				RedisData rd = new RedisData(key, value);
				results.add(rd);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}

		return results;
	}
}