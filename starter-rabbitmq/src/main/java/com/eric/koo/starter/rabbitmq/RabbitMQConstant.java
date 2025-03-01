package com.eric.koo.starter.rabbitmq;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RabbitMQConstant {

    private static final String PROPERTIES_PREFIX_BASE = "rabbitmq.";
    public static final String PROPERTIES_PREFIX_PRODUCER = PROPERTIES_PREFIX_BASE + "producer";
    public static final String PROPERTIES_PREFIX_CONSUMER = PROPERTIES_PREFIX_BASE + "consumer";
}
