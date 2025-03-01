package com.eric.koo.starter.rabbitmq.constraint;

import com.eric.koo.starter.rabbitmq.constraint.validator.IsRabbitMQServiceValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsRabbitMQServiceValidator.class)
public @interface IsRabbitMQService {
    String message() default "{CLASS_NAME} is missing the @RabbitMQService annotation";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
