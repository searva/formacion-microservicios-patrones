package com.in28minutes.microservices.limitsservice;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
	@Bean
	Queue queue(RabbitMQPropertyConfiguration config) {
		return new Queue(config.getQueue(), false);
	}

	
	@Bean
	DirectExchange exchange(RabbitMQPropertyConfiguration config) {
		return new DirectExchange(config.getExchange());
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange, RabbitMQPropertyConfiguration config) {
		return BindingBuilder.bind(queue).to(exchange).with(config.getRoutingkey());
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	
	@Bean
	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
}
