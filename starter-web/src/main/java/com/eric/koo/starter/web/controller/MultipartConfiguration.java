package com.eric.koo.starter.web.controller;

import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
class MultipartConfiguration {

    private final MultipartProperties multipartProperties;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public MultipartConfiguration(MultipartProperties multipartProperties) {
        this.multipartProperties = multipartProperties;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(multipartProperties.getMaxRequestSize().toBytes());
        multipartResolver.setMaxUploadSizePerFile(multipartProperties.getMaxFileSize().toBytes());

        return multipartResolver;
    }
}
