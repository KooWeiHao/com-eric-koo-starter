package com.eric.koo.starter.web.api;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Aspect
@ConditionalOnApiPerformanceMonitor
@Configuration
class ApiPerformanceMonitorConfiguration {

    @Bean
    public PerformanceMonitorInterceptor apiPerformanceMonitorInterceptor() {
        return new ApiPerformanceMonitorInterceptor();
    }

    @Bean
    public Advisor apiPerformanceMonitorAdvisor() {
        var pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("@within(org.springframework.web.bind.annotation.RestController)");

        return new DefaultPointcutAdvisor(pointcut, apiPerformanceMonitorInterceptor());
    }

    @Log4j2
    private static class ApiPerformanceMonitorInterceptor extends PerformanceMonitorInterceptor {

        @Override
        protected void writeToLog(Log logger, String message) {
            // Convert Log4j2 Logger to Commons Logging Log
            var commonsLogger = LogFactory.getLog(log.getName());
            super.writeToLog(commonsLogger, message);
        }
    }
}
