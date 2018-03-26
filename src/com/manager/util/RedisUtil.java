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
 * @Description : redis������
 * @Date : 2018��3��22�� ����2:21:13
 * @Version : V1.0
 */
public class RedisUtil {
	private static JedisPool jedisPool;
	private static Jedis jedis;

	/**
	 * @Title : getConfiguration
	 * @Description : ��ȡ�����ļ���Ϣ
	 * @Date : 2018��3��22�� ����4:11:56
	 * @return
	 */
	private static Properties getConfiguration() {
		Properties properties = new Properties();
		try {
			// ʹ��ClassLoader����properties�����ļ����ɶ�Ӧ��������
			InputStream in = RedisUtil.class.getClassLoader().getResourceAsStream("redisInfo.properties");
			// ʹ��properties�������������
			properties.load(in);
		} catch (Exception e) {
			e.getStackTrace();
		}

		return properties;
	}

	/**
	 * @Title : initRedisPool
	 * @Description : �������ӳ�
	 * @Date : 2018��3��22�� ����3:32:52
	 * @return
	 */
	private static void initRedisPool() {
		// ��ȡ������Ϣ
		Properties properties = getConfiguration();
		// �������ӳ����ò���
		JedisPoolConfig config = new JedisPoolConfig();
		// ��������Ծ������
		config.setMaxTotal(Integer.parseInt(properties.getProperty("maxTotal")));
		// �����������ʱ��
		config.setMaxWaitMillis(Long.valueOf(properties.getProperty("maxWaitMillis")));
		// ����������������
		config.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle")));
		// ������С����������
		config.setMinIdle(Integer.parseInt(properties.getProperty("minIdle")));
		// �������ӳ�
		jedisPool = new JedisPool(config, properties.getProperty("ip"),
				Integer.parseInt(properties.getProperty("port")));
	}

	/**
	 * @Title : getJedis
	 * @Description : ��ȡһ��jedis����
	 * @Date : 2018��3��22�� ����3:33:39
	 * @param args
	 */
	private static Jedis getJedis() {
		// ��ȡ������Ϣ
		Properties properties = getConfiguration();
		// �������ӳ�
		initRedisPool();
		// ��ȡjedis����
		Jedis jedis = jedisPool.getResource();
		// ��������
		jedis.auth(properties.getProperty("passWord"));
		// ѡ�����(0-15)
		jedis.select(Integer.parseInt(properties.getProperty("database")));

		return jedis;
	}

	/**
	 * @Title : returnRes
	 * @Description : �黹����
	 * @Date : 2018��3��22�� ����3:39:50
	 * @param jedis
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	private static void returnRes(Jedis jedis) {
		jedisPool.returnResource(jedis);
	}

	/**
	 * @Title : setRedisData
	 * @Description : �����ݵ�redis
	 * @Date : 2018��3��22�� ����3:44:46
	 * @param key
	 * @param value
	 */
	public static void setRedisData(String key, String value) {
		try {
			// ��ȡjedis����
			if (null == jedis) {
				jedis = getJedis();
			}
			// ������
			jedis.set(key, value);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	/**
	 * @Title : getRedisData
	 * @Description : ��ȡredis����
	 * @Date : 2018��3��22�� ����3:49:15
	 * @param key
	 * @return
	 */
	public static String getRedisData(String key) {
		String value = null;

		try {
			// ��ȡjedis����
			if (null == jedis) {
				jedis = getJedis();
			}
			// ��ȡ����
			value = jedis.get(key);
		} catch (Exception e) {
			e.getStackTrace();
		}

		return value;
	}

	/**
	 * @Title : delRedisData
	 * @Description : ɾ��ָ��key
	 * @Date : 2018��3��23�� ����9:25:22
	 * @param key
	 */
	public static void delRedisData(String key) {
		try {
			// ��ȡjedis����
			if (null == jedis) {
				jedis = getJedis();
			}
			// ɾ������
			jedis.del(key);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	/**
	 * @Title : getRedisDataByPattern
	 * @Description : ��ȡָ��ǰ׺key����������
	 * @Date : 2018��3��23�� ����9:36:49
	 * @param pattern
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<RedisData> getRedisDataByPattern(String pattern) {
		List<RedisData> results = new ArrayList<>();
		String key = null;
		String value = null;

		try {
			// ��ȡjedis����
			if (null == jedis) {
				jedis = getJedis();
			}
			// ��ȡָ��ǰ׺������key
			Set<String> keys = jedis.keys(pattern + "*");
			// ����key��ȡ���ݷ���list
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