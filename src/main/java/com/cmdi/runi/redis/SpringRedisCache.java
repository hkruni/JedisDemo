package com.cmdi.runi.redis;

import java.io.InputStream;
import java.util.Properties;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class SpringRedisCache implements Cache {

	private String name;

	private int DATABASE = 1;// 访问的具体数据库

	private JedisPool masterPool;
	private String password;

	public SpringRedisCache() {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream infile = cl.getResourceAsStream("redis.properties");
		Properties props = new Properties();
		String masterIp = "";
		int masterPort = 0;
		try {
			props.load(infile);
			masterIp = props.getProperty("redis.host");
			masterPort = Integer.parseInt(props.getProperty("redis.port"));
			password = props.getProperty("password");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 创建jedis 连接池
		JedisPoolConfig config = new JedisPoolConfig();
		masterPool = new JedisPool(config, masterIp, masterPort);
	}

	public void setName(String name) {
		System.out.println(name);
		this.name = name;
	}

	@Override
	public void clear() {
		Jedis jedis = null;
		try {
			jedis = masterPool.getResource();

			if (password != null && !password.equals(""))
				jedis.auth(password);
			jedis.select(DATABASE);
			jedis.flushDB();
		} finally {
			masterPool.returnResource(jedis);
		}

	}

	@Override
	public void evict(Object key) {
		Jedis jedis = null;
		try {
			jedis = masterPool.getResource();
			jedis.del(key.toString().getBytes());
		} finally {
			masterPool.returnResource(jedis);
		}

	}

	@Override
	public ValueWrapper get(Object key) {
		System.out.println("redis get");
		Jedis jedis = null;
		Object value = null;
		try {
			jedis = masterPool.getResource();
			if (password != null && !password.equals(""))
				jedis.auth(password);
			jedis.select(DATABASE);

			value = ObjectByteArrayUtils.toObject(jedis.get(key.toString()
					.getBytes()));
		} catch (Exception e) {
			System.out.println(StringUtils.printStackTrace(e));
		} finally {
			masterPool.returnResource(jedis);
		}
		return (value != null ? new SimpleValueWrapper(value) : null);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public Object getNativeCache() {
		// TODO Auto-generated method stub
		System.out.println("getNativeCache");
		return null;
	}

	@Override
	public void put(Object key, Object value) {
		Jedis jedis = null;
		try {
			jedis = masterPool.getResource();
			if (password != null && !password.equals(""))
				jedis.auth(password);
			jedis.select(DATABASE);
			jedis.set(key.toString().getBytes(),
					ObjectByteArrayUtils.toBytes(value));

		} catch (Exception e) {
			System.out.println(StringUtils.printStackTrace(e));
		} finally {
			masterPool.returnResource(jedis);
		}

	}

}
