package com.eric.koo.starter.web.mvc;

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
}
