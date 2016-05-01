package com.cmdi.runi.clinetTest;

import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cmdi.runi.model.message.Message;
import com.cmdi.runi.service.PublishService;

public class Publisher {

	/**
	 * @date：2016-4-24
	 * @author：hukai
	 * @param @param args todo：TODO
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		PublishService publisherService = (PublishService) context
				.getBean("publishService");

		Message message = new Message();
		message.setId(UUID.randomUUID().toString());
		message.setContent("my fisrt hukai demo");
		publisherService.publishMessage("channel1", message);

	}
}
