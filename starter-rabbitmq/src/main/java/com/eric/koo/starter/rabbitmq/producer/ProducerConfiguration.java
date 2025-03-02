package com.eric.koo.starter.rabbitmq.producer;

import com.eric.koo.starter.rabbitmq.RabbitMQQueue;
import com.eric.koo.starter.rabbitmq.producer.service.ProducerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.remoting.service.AmqpInvokerServiceExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
class ProducerConfiguration {

    private final ProducerProperties producerProperties;

    ProducerConfiguration(ProducerProperties producerProperties) {
        this.producerProperties = producerProperties;
    }

    @Bean
    public DirectExchange directExchange() {
        var name = String.format("remoting.exchange.%s", producerProperties.getSystemCode());

        return ExchangeBuilder
                .directExchange(name)
                .build();
    }

    @SuppressWarnings({"deprecation", "SpringJavaInjectionPointsAutowiringInspection"})
    @Bean
    public AmqpInvokerServiceExporter amqpInvokerServiceExporter(ProducerService producerService, AmqpTemplate amqpTemplate) {
        var amqpInvokerServiceExporter = new AmqpInvokerServiceExporter();
        amqpInvokerServiceExporter.setServiceInterface(ProducerService.class);
        amqpInvokerServiceExporter.setService(producerService);
        amqpInvokerServiceExporter.setAmqpTemplate(amqpTemplate);

        return amqpInvokerServiceExporter;
    }

    @SuppressWarnings({"deprecation", "SpringJavaInjectionPointsAutowiringInspection"})
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainerForTransaction(DirectExchange directExchange, AmqpAdmin amqpAdmin, ConnectionFactory connectionFactory, AmqpInvokerServiceExporter amqpInvokerServiceExporter) {
        return getSimpleMessageListenerContainer(RabbitMQQueue.TRANSACTION, directExchange, amqpAdmin, connectionFactory, amqpInvokerServiceExporter, producerProperties.getTransactionListener().getConcurrentConsumers());
    }

    @SuppressWarnings({"deprecation", "SpringJavaInjectionPointsAutowiringInspection"})
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainerForInformation(DirectExchange directExchange, AmqpAdmin amqpAdmin, ConnectionFactory connectionFactory, AmqpInvokerServiceExporter amqpInvokerServiceExporter) {
        return getSimpleMessageListenerContainer(RabbitMQQueue.INFORMATION, directExchange, amqpAdmin, connectionFactory, amqpInvokerServiceExporter, producerProperties.getInformationListener().getConcurrentConsumers());
    }

    @SuppressWarnings("deprecation")
    private SimpleMessageListenerContainer getSimpleMessageListenerContainer(RabbitMQQueue rabbitMQQueue, DirectExchange directExchange, AmqpAdmin amqpAdmin, ConnectionFactory connectionFactory, AmqpInvokerServiceExporter amqpInvokerServiceExporter, int concurrentConsumers) {
        var queue = QueueBuilder
                .durable(String.format("%s.%s", producerProperties.getSystemCode(), rabbitMQQueue.getQueue()))
                .build();
        var binding = BindingBuilder
                .bind(queue)
                .to(directExchange)
                .with(String.format("remoting.binding.%s.%s", producerProperties.getSystemCode(), rabbitMQQueue.getQueue()));
        log.debug("{}; {}; SimpleMessageListenerContainer [concurrentConsumers={}]", queue, binding, concurrentConsumers);

        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(binding);

        var simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setMessageListener(amqpInvokerServiceExporter);
        simpleMessageListenerContainer.setQueues(queue);
        simpleMessageListenerContainer.setConcurrentConsumers(concurrentConsumers);

        return simpleMessageListenerContainer;
    }
}
