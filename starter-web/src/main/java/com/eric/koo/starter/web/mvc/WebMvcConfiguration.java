package com.eric.koo.starter.web.mvc;

import com.eric.koo.starter.web.WebConstant;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;

@Log4j2
@Configuration
class WebMvcConfiguration implements WebMvcConfigurer {

    private final CorsProperties corsProperties;

    WebMvcConfiguration(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.debug(corsProperties);

        var corsRegistration = registry.addMapping(WebConstant.PATH_PATTERN_ANY)
                .allowedOriginPatterns(corsProperties.getAllowedOrigins())
                .allowedMethods(corsProperties.getAllowedMethods())
                .allowedHeaders(corsProperties.getAllowedHeaders())
                .allowCredentials(corsProperties.isAllowedCredentials())
                .maxAge(corsProperties.getMaxAge());

        Optional.ofNullable(corsProperties.getExposedHeaders())
                .ifPresent(corsRegistration::exposedHeaders);
    }
}
