package com.eric.koo.starter.rabbitmq.producer.service;

import com.eric.koo.starter.rabbitmq.constraint.IsRabbitMQService;

import javax.validation.constraints.NotBlank;

@FunctionalInterface
public interface ProducerService {
    Object invoke(@NotBlank String requestReferenceId, @IsRabbitMQService Class<?> serviceClass, @NotBlank String methodName, Class<?>[] parameterTypes, Object[] args);
}
