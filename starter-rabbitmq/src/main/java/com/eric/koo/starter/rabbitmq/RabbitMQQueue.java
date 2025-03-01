package com.eric.koo.starter.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RabbitMQQueue {
    TRANSACTION("transaction"),
    INFORMATION("information");

    private final String queue;
}
