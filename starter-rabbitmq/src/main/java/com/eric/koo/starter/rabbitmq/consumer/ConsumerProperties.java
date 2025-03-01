package com.eric.koo.starter.rabbitmq.consumer;

import com.eric.koo.starter.rabbitmq.RabbitMQConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = RabbitMQConstant.PROPERTIES_PREFIX_CONSUMER)
public class ConsumerProperties {

    private Duration timeout = Duration.ofSeconds(5);
}
