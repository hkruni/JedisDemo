package com.cmdi.runi.service;

import java.util.Map;

import com.cmdi.runi.redis.JedisUtil;

public class CounterService {

	/**
	 * 需要统计每秒，每五秒，每分钟，每五分钟，每小时，每五小时，每天的访问量
	 */
	public static final int[] PRECISION = new int[] { 1, 5, 60, 300, 3600,
			18000, 86400 };

	public static String COUNT = "count:";
	public static String KNOW = "know:";

	/**
	 * @date：2016-4-29
	 * @author：hukai
	 * @param： todo：更新计数器
	 */
	public void updateCounter(String name) {
		long now = System.currentTimeMillis() / 1000;

		for (int prec : PRECISION) {
			long pnow = (now / prec) * prec;// 得到当前时间片的开始时间
			String key = COUNT + name + ":" + String.valueOf(prec);
			JedisUtil.hincrby(key, String.valueOf(pnow), 1);
		}
	}

	/**
	 * @date：2016-5-1
	 * @author：hukai
	 * @param：prec时间片 todo：得到某个时间片的计数器散列值
	 */
	public Map getCounter(String name, int prec) {
		String key = COUNT + name + ":" + String.valueOf(prec);
		Map<String, String> map = JedisUtil.hgetAll(key);
		return map;
	}
}
