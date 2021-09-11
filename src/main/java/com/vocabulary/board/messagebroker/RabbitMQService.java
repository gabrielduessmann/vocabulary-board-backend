package com.vocabulary.board.messagebroker;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String queueName, Object objectMessage) {
        rabbitTemplate.convertAndSend(queueName, objectMessage);
    }
}
