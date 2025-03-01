package com.eric.koo.starter.rabbitmq.consumer.service.impl;

import com.eric.koo.starter.rabbitmq.RabbitMQService;
import com.eric.koo.starter.rabbitmq.consumer.ConsumerProperties;
import com.eric.koo.starter.rabbitmq.consumer.service.ConsumerService;
import com.eric.koo.starter.rabbitmq.producer.service.ProducerService;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.remoting.client.AmqpProxyFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.Proxy;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Validated
@Service
class ConsumerServiceImpl implements ConsumerService {

    private static final Map<Key, Object> CACHE_MAP = new ConcurrentHashMap<>();

    private final ConnectionFactory connectionFactory;
    private final ConsumerProperties consumerProperties;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    ConsumerServiceImpl(ConnectionFactory connectionFactory, ConsumerProperties consumerProperties) {
        this.connectionFactory = connectionFactory;
        this.consumerProperties = consumerProperties;
    }

    @SuppressWarnings("deprecation")
    private <T> AmqpProxyFactoryBean getAmqpProxyFactoryBean(Class<T> serviceClass, String targetSystemCode, Duration timeout) {
        var rabbitMQService = serviceClass.getAnnotation(RabbitMQService.class);
        var queue = rabbitMQService.queue().getQueue();

        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(String.format("remoting.exchange.%s", targetSystemCode));
        rabbitTemplate.setRoutingKey(String.format("remoting.binding.%s.%s", targetSystemCode, queue));
        rabbitTemplate.setReplyTimeout(timeout.toMillis());

        var amqpProxyFactoryBean = new AmqpProxyFactoryBean();
        amqpProxyFactoryBean.setAmqpTemplate(rabbitTemplate);
        amqpProxyFactoryBean.setServiceInterface(ProducerService.class);
        amqpProxyFactoryBean.afterPropertiesSet();

        return amqpProxyFactoryBean;
    }

    @Override
    public <T> T create(String targetSystemCode, Class<T> serviceClass, Duration timeout) {
        var proxyInstance = CACHE_MAP.computeIfAbsent(
                new Key(targetSystemCode, serviceClass, timeout),
                key -> {
                    var amqpProxyFactoryBean = getAmqpProxyFactoryBean(key.serviceClass, key.targetSystemCode, key.timeout);

                    return Proxy.newProxyInstance(
                            this.getClass().getClassLoader(),
                            new Class[] {key.serviceClass},
                            (proxy, method, args) -> {
                                log.debug("Sending request - method: [{}], class: [{}]", method.getName(), serviceClass.getName());
                                var producerService = (ProducerService) amqpProxyFactoryBean.getObject();
                                assert producerService != null;
                                return producerService.invoke(ThreadContext.peek(), key.serviceClass, method.getName(), method.getParameterTypes(), args);
                            }
                    );
                }
        );

        return serviceClass.cast(proxyInstance);
    }

    @Override
    public <T> T create(String targetSystemCode, Class<T> serviceClass) {
        return create(targetSystemCode, serviceClass, consumerProperties.getTimeout());
    }

    @EqualsAndHashCode(cacheStrategy = EqualsAndHashCode.CacheStrategy.LAZY)
    @AllArgsConstructor
    private static class Key {
        private String targetSystemCode;
        private Class<?> serviceClass;
        private Duration timeout;
    }
}
