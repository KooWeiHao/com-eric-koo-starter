package com.eric.koo.starter.rabbitmq;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Log4j2
@Configuration
@ComponentScan(basePackageClasses = RabbitMQConfiguration.class)
class RabbitMQConfiguration {

    @PostConstruct
    private void init() {
        log.info("{}", "[Starter RabbitMQ] is enabled");
    }
}
