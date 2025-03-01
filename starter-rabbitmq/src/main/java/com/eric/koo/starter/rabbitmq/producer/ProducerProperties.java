package com.eric.koo.starter.rabbitmq.producer;

import com.eric.koo.starter.rabbitmq.RabbitMQConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Validated
@Configuration
@ConfigurationProperties(prefix = RabbitMQConstant.PROPERTIES_PREFIX_PRODUCER)
public class ProducerProperties {

    @NotBlank
    private String systemCode;

    private ProducerListenerProperties transactionListener = new ProducerListenerProperties();

    private ProducerListenerProperties informationListener = new ProducerListenerProperties();

    @Getter
    @Setter
    @ToString
    public static class ProducerListenerProperties {

        private int concurrentConsumers = 1;
    }
}
