package com.eric.koo.starter.web;

import io.undertow.util.Methods;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = WebConstant.PROPERTIES_PREFIX_CORS)
public class CorsProperties {

    private String[] allowedOrigins = {CorsConfiguration.ALL};
    private String[] allowedMethods = {Methods.GET_STRING, Methods.POST_STRING, Methods.PUT_STRING, Methods.PATCH_STRING, Methods.DELETE_STRING, Methods.OPTIONS_STRING};
    private String[] allowedHeaders = {CorsConfiguration.ALL};
    private boolean allowedCredentials = true;
    private long maxAge = 3600;
    private String[] exposedHeaders;
}
