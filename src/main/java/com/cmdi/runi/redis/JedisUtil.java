package com.cmdi.runi.redis;

import java.io.InputStream;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {

	private static JedisPool jedisPool;
	static {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream infile = cl.getResourceAsStream("redis.properties");
		Properties props = new Properties();
		String masterIp = "";
		String password = "";
		int masterPort = 0;
		try {
			props.load(infile);
			masterIp = props.getProperty("redis-server-master-ip");
			masterPort = Integer.parseInt(props
					.getProperty("redis-server-master-port"));
			password = props.getProperty("password");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JedisPoolConfig config = new JedisPoolConfig();
		jedisPool = new JedisPool(config, masterIp, masterPort);
	}

	public static Jedis getJedis() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
		}

		return jedis;
	}

	public static void close(Jedis jedis) {
		try {
			jedisPool.returnResource(jedis);

		} catch (Exception e) {
			if (jedis.isConnected()) {
				jedis.quit();
				jedis.disconnect();
			}
		}
	}

	public static String get(String key) {

		String value = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);
		}
		return value;
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public static byte[] get(byte[] key) {

		byte[] value = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);
		}

		return value;
	}

	public static void set(byte[] key, byte[] value) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);
		}
	}

	public static void set(byte[] key, byte[] value, int time) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);
			jedis.expire(key, time);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);
		}
	}

	public static void hset(byte[] key, byte[] field, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.hset(key, field, value);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);
		}
	}

	public static void hset(String key, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.hset(key, field, value);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);
		}
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public static String hget(String key, String field) {

		String value = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			value = jedis.hget(key, field);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);
		}

		return value;
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public static byte[] hget(byte[] key, byte[] field) {

		byte[] value = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			value = jedis.hget(key, field);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);
		}

		return value;
	}

	public static void hdel(byte[] key, byte[] field) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.hdel(key, field);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);
		}
	}

	/**
	 * 存储REDIS队列 顺序存储
	 * 
	 * @param byte[] key reids键名
	 * @param byte[] value 键值
	 */
	public static void lpush(byte[] key, byte[] value) {

		Jedis jedis = null;
		try {

			jedis = jedisPool.getResource();
			jedis.lpush(key, value);

		} catch (Exception e) {

			// 释放redis对象
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

		} finally {

			// 返还到连接池
			close(jedis);

		}
	}

	/**
	 * 存储REDIS队列 反向存储
	 * 
	 * @param byte[] key reids键名
	 * @param byte[] value 键值
	 */
	public static void rpush(byte[] key, byte[] value) {

		Jedis jedis = null;
		try {

			jedis = jedisPool.getResource();
			jedis.rpush(key, value);

		} catch (Exception e) {

			// 释放redis对象
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

		} finally {

			// 返还到连接池
			close(jedis);

		}
	}

	/**
	 * 将列表 source 中的最后一个元素(尾元素)弹出，并返回给客户端
	 * 
	 * @param byte[] key reids键名
	 * @param byte[] value 键值
	 */
	public static void rpoplpush(byte[] key, byte[] destination) {

		Jedis jedis = null;
		try {

			jedis = jedisPool.getResource();
			jedis.rpoplpush(key, destination);

		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

		} finally {
			close(jedis);
		}
	}

	/**
	 * 获取队列数据
	 * 
	 * @param byte[] key 键名
	 * @return
	 */
	public static List<byte[]> lpopList(byte[] key) {

		List<byte[]> list = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			list = jedis.lrange(key, 0, -1);

		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);
		}
		return list;
	}

	/**
	 * 获取队列数据
	 * 
	 * @param byte[] key 键名
	 * @return
	 */
	public static byte[] rpop(byte[] key) {

		byte[] bytes = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			bytes = jedis.rpop(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);

		}
		return bytes;
	}

	public static void lrem(byte[] key, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			/**
			 * count 的值可以是以下几种： count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count
			 * count < 0 :从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。 count = 0:
			 * 移除表中所有与value 相等的值。
			 */
			jedis.lrem(key, 0, value);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);
		}
	}

	public static void ltrim(byte[] key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.ltrim(key, start, end);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);
		}
	}

	public static void hmset(Object key, Map<String, String> hash) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.hmset(key.toString(), hash);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);
		}
	}

	public static void hmset(Object key, Map<String, String> hash, int time) {
		Jedis jedis = null;
		try {

			jedis = jedisPool.getResource();
			jedis.hmset(key.toString(), hash);
			jedis.expire(key.toString(), time);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);

		}
	}

	public static List<String> hmget(Object key, String... fields) {
		List<String> result = null;
		Jedis jedis = null;
		try {

			jedis = jedisPool.getResource();
			result = jedis.hmget(key.toString(), fields);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);

		}
		return result;
	}

	public static Set<String> hkeys(String key) {
		Set<String> result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.hkeys(key);

		} catch (Exception e) {
			// 释放redis对象
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

		} finally {
			// 返还到连接池
			close(jedis);

		}
		return result;
	}

	public static void hincrby(String key, String hash, int count) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.hincrBy(key, hash, count);

		} catch (Exception e) {
			// 释放redis对象
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

		} finally {
			// 返还到连接池
			close(jedis);
		}
	}

	public static Map hgetAll(String key) {
		Jedis jedis = null;
		Map<String, String> map = null;
		try {
			jedis = jedisPool.getResource();
			map = jedis.hgetAll(key);

		} catch (Exception e) {
			// 释放redis对象
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

		} finally {
			// 返还到连接池
			close(jedis);
		}

		return map;
	}

	public static List<byte[]> lrange(byte[] key, int from, int to) {
		List<byte[]> result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.lrange(key, from, to);

		} catch (Exception e) {
			// 释放redis对象
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

		} finally {
			// 返还到连接池
			close(jedis);
		}
		return result;
	}

	public static Map<byte[], byte[]> hgetAll(byte[] key) {
		Map<byte[], byte[]> result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.hgetAll(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);
		}
		return result;
	}

	public static void del(byte[] key) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);
		}
	}

	public static long llen(byte[] key) {
		long len = 0;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.llen(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);
		}
		return len;
	}

	public static void publish(byte[] channel, byte[] message) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.publish(channel, message);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);
		}
	}

	public static void subscribe(BinaryJedisPubSub jedisPubSub, byte[] channel) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.subscribe(jedisPubSub, channel);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);
		}
	}

	/**
	 * @date：2016-5-1
	 * @author：hukai
	 * @param： todo：TODO 测试用的，不要
	 */
	public static void setbit() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.setbit("keys5", 1, true);
			jedis.setbit("keys5", 29, true);
			jedis.setbit("keys5", 117, true);
			BitSet bitset = BitSet.valueOf(jedis.get("keys5").getBytes());
			System.out.println(jedis.get("keys5").getBytes().length);
			System.out.println(bitset.size());
			System.out.println(bitset.cardinality());

		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			close(jedis);
		}
	}

	public static void main(String[] args) {

	}

}
