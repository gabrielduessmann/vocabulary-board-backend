package com.vocabulary.board.messagebroker;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RabbitMQConnection {

    private static final String EXCHANGE_NAME = "amq.direct";
    private AmqpAdmin amqpAdmin;

    public RabbitMQConnection(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    public Queue queue(String queueName) {
        return new Queue(queueName, true, false, false);
    }

    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    public Binding binding(Queue queue, DirectExchange directExchange) {
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, directExchange.getName(), queue.getName(), null);
    }

    @PostConstruct
    public void addQueue() {
        Queue vocabularyQueue = queue(RabbitMQConstants.VOCABULARY_QUEUE);
        DirectExchange directExchange = directExchange();
        Binding bindingVocabulary = binding(vocabularyQueue, directExchange);

        // Create queue in RabbitMQ
        amqpAdmin.declareQueue(vocabularyQueue);
        amqpAdmin.declareExchange(directExchange);
        amqpAdmin.declareBinding(bindingVocabulary);

    }
}
