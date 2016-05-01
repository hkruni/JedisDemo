package com.cmdi.runi.service;

import java.util.List;

import redis.clients.jedis.Jedis;

import com.cmdi.runi.model.Look;
import com.cmdi.runi.redis.JedisUtil;
import com.cmdi.runi.redis.ObjectByteArrayUtils;
import com.google.common.collect.Lists;

public class HomeLinkService {

	public static String LOOK = "look:";

	/**
	 * @throws Exception
	 * @date：2016-5-1
	 * @author：hukai
	 * @param： todo：添加带看记录
	 */
	public void addLookRecord(String key, String boker, String date)
			throws Exception {

		Look look = new Look();
		look.setHouseId(key);
		look.setBoker(boker);
		look.setTime(date);

		Jedis jedis = JedisUtil.getJedis();
		jedis.lpush((LOOK + key).getBytes(), ObjectByteArrayUtils.toBytes(look));
		JedisUtil.close(jedis);

	}

	/**
	 * @date：2016-5-1
	 * @author：hukai
	 * @param： todo：获取某个房源的带看记录
	 */
	public List<Look> getLookRecord(String key) throws Exception {
		List<Look> looks = Lists.newArrayList();
		List<byte[]> result = JedisUtil.lrange((LOOK + key).getBytes(), 0, -1);
		for (byte[] b : result) {
			Look look = (Look) ObjectByteArrayUtils.toObject(b);
			looks.add(look);
		}
		return looks;
	}

	public static void main(String[] args) throws Exception {
		HomeLinkService home = new HomeLinkService();
		// home.addLookRecord("010122212356", "胡凯", "2015-06-01");
		// home.addLookRecord("010122212356", "邹丽菲", "2015-06-01");
		home.addLookRecord("010122212356", "得爽", "2015-07-01");
		List<Look> list = home.getLookRecord("010122212356");
		for (Look l : list) {
			System.out.println(l.toString());
		}

	}
}
