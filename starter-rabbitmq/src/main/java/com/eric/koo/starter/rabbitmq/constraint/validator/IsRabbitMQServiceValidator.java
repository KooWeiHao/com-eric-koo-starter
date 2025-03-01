package com.eric.koo.starter.rabbitmq.constraint.validator;

import com.eric.koo.starter.rabbitmq.RabbitMQService;
import com.eric.koo.starter.rabbitmq.constraint.IsRabbitMQService;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class IsRabbitMQServiceValidator implements ConstraintValidator<IsRabbitMQService, Class<?>> {

    @Override
    public boolean isValid(Class<?> serviceClass, ConstraintValidatorContext constraintValidatorContext) {
        return Optional.ofNullable(serviceClass)
                .map(sClass -> {
                    // Prepare error message
                    constraintValidatorContext.unwrap(HibernateConstraintValidatorContext.class)
                            .addMessageParameter("CLASS_NAME", serviceClass.getName());

                    return sClass.isAnnotationPresent(RabbitMQService.class);
                }).orElseGet(() -> {
                    // Prepare error message
                    constraintValidatorContext.disableDefaultConstraintViolation();
                    constraintValidatorContext.buildConstraintViolationWithTemplate("must not be null")
                            .addConstraintViolation();

                    return false;
                });
    }
}
