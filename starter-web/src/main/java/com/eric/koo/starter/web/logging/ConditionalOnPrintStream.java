package com.eric.koo.starter.web.logging;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ConditionalOnExpression(value = "T(org.apache.commons.lang3.StringUtils).isNotBlank('${web.logging.print-stream-packages:}')")
public @interface ConditionalOnPrintStream {
}
