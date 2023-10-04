package com.eric.koo.starter.web.controller;

import com.eric.koo.starter.web.WebConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = WebConstant.PROPERTIES_PREFIX_STANDARD_EXCEPTION_HANDLER)
public class StandardExceptionHandlerProperties {

    private boolean printStackTraceEnabled;
}
