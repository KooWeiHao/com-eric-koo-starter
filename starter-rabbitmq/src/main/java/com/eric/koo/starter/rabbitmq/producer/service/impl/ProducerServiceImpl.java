package com.eric.koo.starter.rabbitmq.producer.service.impl;

import com.eric.koo.starter.rabbitmq.producer.ProducerException;
import com.eric.koo.starter.rabbitmq.producer.service.ProducerService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.core.util.Throwables;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Log4j2
@Validated
@Service
class ProducerServiceImpl implements ProducerService {

    private final ApplicationContext applicationContext;

    public ProducerServiceImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object invoke(String requestReferenceId, Class<?> serviceClass, String methodName, Class<?>[] parameterTypes, Object[] args) {
        var closeableThreadContext = CloseableThreadContext.push(requestReferenceId);

        try {
            log.debug("Processing request - method: [{}], class: [{}]", methodName, serviceClass.getName());
            var serviceImpl = applicationContext.getBean(serviceClass);

            return serviceClass.getDeclaredMethod(methodName, parameterTypes)
                    .invoke(serviceImpl, args);
        } catch (Exception e) {
            log.error(Throwables.getRootCause(e).getMessage(), e);
            throw new ProducerException(String.format("Error processing request - method: [%s], class: [%s]", methodName, serviceClass.getName()));
        } finally {
            // To avoid threadContext is closed before printing the error log
            closeableThreadContext.close();
        }
    }
}
