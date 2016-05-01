package com.cmdi.runi.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import com.cmdi.runi.redis.JedisUtil;

public class AutoCompleteService {

	private static final String VALID_CHARACTERS = "`abcdefghijklmnopqrstuvwxyz{";

	/**
	 * @date：2016-5-1
	 * @author：hukai
	 * @param： todo：根据搜索的前缀找到结果的起始位置的前一个和后一个元素
	 */
	private String[] findPrefixRange(String prefix) {
		int posn = VALID_CHARACTERS.indexOf(prefix.charAt(prefix.length() - 1));
		char suffix = VALID_CHARACTERS.charAt(posn > 0 ? posn - 1 : 0);
		String start = prefix.substring(0, prefix.length() - 1) + suffix + '{';
		String end = prefix + '{';
		return new String[] { start, end };
	}

	public Set<String> autocompleteOnPrefix(String guild, String prefix) {

		Jedis conn = JedisUtil.getJedis();
		if (conn == null)
			return null;

		String[] range = findPrefixRange(prefix);
		String start = range[0];
		String end = range[1];
		String identifier = UUID.randomUUID().toString();
		start += identifier;
		end += identifier;
		String zsetName = "members:" + guild;

		conn.zadd(zsetName, 0, start);
		conn.zadd(zsetName, 0, end);

		Set<String> items = null;
		while (true) {
			conn.watch(zsetName);
			int sindex = conn.zrank(zsetName, start).intValue();
			int eindex = conn.zrank(zsetName, end).intValue();
			int erange = Math.min(sindex + 9, eindex - 2);

			Transaction trans = conn.multi();
			trans.zrem(zsetName, start);
			trans.zrem(zsetName, end);
			trans.zrange(zsetName, sindex, erange);
			List<Object> results = trans.exec();
			if (results != null) {
				items = (Set<String>) results.get(results.size() - 1);
				break;
			}
		}
		JedisUtil.close(conn);
		for (Iterator<String> iterator = items.iterator(); iterator.hasNext();) {
			if (iterator.next().indexOf('{') != -1) {
				iterator.remove();
			}
		}
		return items;
	}
}
