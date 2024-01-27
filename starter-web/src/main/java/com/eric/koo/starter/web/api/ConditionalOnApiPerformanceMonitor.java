package com.eric.koo.starter.web.api;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ConditionalOnExpression(value = "${web.api.performance-monitor-enabled:true}") //TODO: Might consider to create custom condition class to use ApiProperties
public @interface ConditionalOnApiPerformanceMonitor {
}
