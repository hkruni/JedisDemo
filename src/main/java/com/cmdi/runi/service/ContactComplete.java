package com.cmdi.runi.service;

import java.util.List;

import com.cmdi.runi.redis.JedisUtil;
import com.google.common.collect.Lists;

/**
 * @date 2016-4-29
 * @author hukai
 * @todo 自动补充最近联系人相关功能实现,每次保存10个
 */
public class ContactComplete {

	public static String KEY_USERS = "users:";

	/**
	 * @date：2016-4-29
	 * @author：hukai
	 * @param @param userId
	 * @param @param ContactId todo：更新最近联系人
	 */
	public void UpdateContact(String userId, String ContactId) {
		JedisUtil.lrem((KEY_USERS + userId).getBytes(), ContactId.getBytes());
		JedisUtil.lpush((KEY_USERS + userId).getBytes(), ContactId.getBytes());
		JedisUtil.ltrim((KEY_USERS + userId).getBytes(), 0, 10);
	}

	/**
	 * @date：2016-4-29
	 * @author：hukai
	 * @param： todo：删除联系人
	 */
	public void removeContact(String userId, String ContactId) {
		JedisUtil.lrem((KEY_USERS + userId).getBytes(), ContactId.getBytes());
	}

	/**
	 * @date：2016-4-29
	 * @author：hukai
	 * @param： todo：自动补充
	 */
	public List<String> fetchAutocompleteList(String userId, String prefix) {
		List<String> allContact = Lists.newArrayList();
		List<byte[]> list = JedisUtil.lrange((KEY_USERS + userId).getBytes(),
				0, -1);
		for (byte[] t : list) {
			String contact = new String(t);
			if (contact.startsWith(prefix)) {
				allContact.add(userId);
			}
		}

		return allContact;
	}
}
