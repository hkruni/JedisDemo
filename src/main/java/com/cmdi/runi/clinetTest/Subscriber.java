package com.cmdi.runi.clinetTest;

import com.cmdi.runi.redis.JedisUtil;
import com.cmdi.runi.service.SubscribeService;

public class Subscriber {

	public static void main(String[] args) {
		JedisUtil.subscribe(new SubscribeService(), "channel".getBytes());
	}
}
