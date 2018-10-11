package com.in28minutes.microservices.limitsservice;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {
	
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Autowired
	RabbitMQPropertyConfiguration configuration;
	
	public void send(LimitsConfiguration limits) {
		rabbitTemplate.convertAndSend(configuration.getExchange(), configuration.getRoutingkey(), limits);
		System.out.println("Send msg = " + limits);
	    
	}
	
	
}