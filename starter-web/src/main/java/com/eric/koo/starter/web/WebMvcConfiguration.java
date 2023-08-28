package com.eric.koo.starter.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;

@Log4j2
@Configuration
class WebMvcConfiguration implements WebMvcConfigurer {

    private final WebProperties webProperties;

    WebMvcConfiguration(WebProperties webProperties) {
        this.webProperties = webProperties;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var cors = webProperties.getCors();
        var corsRegistration = registry.addMapping(WebConstant.PATH_PATTERN_ANY)
                .allowedOriginPatterns(cors.getAllowedOrigins())
                .allowedMethods(cors.getAllowedMethods())
                .allowedHeaders(cors.getAllowedHeaders())
                .allowCredentials(cors.isAllowedCredentials())
                .maxAge(cors.getMaxAge());

        Optional.ofNullable(cors.getExposedHeaders())
                .ifPresent(corsRegistration::exposedHeaders);

        log.debug(cors);
    }
}
