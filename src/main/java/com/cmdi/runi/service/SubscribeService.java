package com.cmdi.runi.service;

import redis.clients.jedis.BinaryJedisPubSub;

import com.cmdi.runi.model.message.Message;
import com.cmdi.runi.redis.ObjectByteArrayUtils;

public class SubscribeService extends BinaryJedisPubSub {

	@Override
	public void onMessage(byte[] channel, byte[] message) {
		String channel1 = new String(channel);
		Message message1 = null;
		try {
			message1 = (Message) ObjectByteArrayUtils.toObject(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("channel1 :" + channel1);
		System.out.println("Message : " + message1.getContent());

	}

	@Override
	public void onPMessage(byte[] pattern, byte[] channel, byte[] message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSubscribe(byte[] channel, int subscribedChannels) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUnsubscribe(byte[] channel, int subscribedChannels) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPUnsubscribe(byte[] pattern, int subscribedChannels) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPSubscribe(byte[] pattern, int subscribedChannels) {
		// TODO Auto-generated method stub

	}

}
