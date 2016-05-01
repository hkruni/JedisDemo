package com.cmdi.runi.service;

import org.springframework.stereotype.Service;

import com.cmdi.runi.model.message.Message;
import com.cmdi.runi.redis.JedisUtil;
import com.cmdi.runi.redis.ObjectByteArrayUtils;

@Service
public class PublishService {

	public void publishMessage(String channel, Message message)
			throws Exception {
		JedisUtil.publish(channel.getBytes(),
				ObjectByteArrayUtils.toBytes(message));
	}

}
