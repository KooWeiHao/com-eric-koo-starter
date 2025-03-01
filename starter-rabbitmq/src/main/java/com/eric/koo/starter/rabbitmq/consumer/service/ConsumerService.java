package com.eric.koo.starter.rabbitmq.consumer.service;

import com.eric.koo.starter.rabbitmq.constraint.IsRabbitMQService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;

public interface ConsumerService {
    <T> T create(@NotBlank String targetSystemCode, @IsRabbitMQService Class<T> serviceClass, @NotNull Duration timeout);
    <T> T create(@NotBlank String targetSystemCode, @IsRabbitMQService Class<T> serviceClass);
}
