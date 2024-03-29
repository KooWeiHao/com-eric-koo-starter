package com.eric.koo.starter.web.mvc;

import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
class MultipartConfiguration {

    private final MultipartProperties multipartProperties;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    MultipartConfiguration(MultipartProperties multipartProperties) {
        this.multipartProperties = multipartProperties;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        var multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(multipartProperties.getMaxRequestSize().toBytes());
        multipartResolver.setMaxUploadSizePerFile(multipartProperties.getMaxFileSize().toBytes());

        return multipartResolver;
    }
}
