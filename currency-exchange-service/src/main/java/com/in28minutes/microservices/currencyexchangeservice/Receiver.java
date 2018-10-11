package com.in28minutes.microservices.currencyexchangeservice;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {


    @RabbitListener(queues="${limits-event.rabbitmq.queue}")
    public void receiveMessage(LimitsConfiguration message) {
        System.out.println("Received <" + message + ">");
    }

  

}