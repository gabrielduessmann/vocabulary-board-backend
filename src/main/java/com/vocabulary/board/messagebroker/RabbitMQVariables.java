package com.vocabulary.board.messagebroker;

import org.springframework.beans.factory.annotation.Value;

public class RabbitMQVariables {
    @Value("${localconfig.rabbitmq.active}")
    public static boolean rabbitmqActive;
}