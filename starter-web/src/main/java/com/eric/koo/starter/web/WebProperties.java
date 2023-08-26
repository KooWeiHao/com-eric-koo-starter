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
@ConfigurationProperties(prefix = WebConstants.TAG)
public class WebProperties {
    private Cors cors = new Cors();

    @Getter
    @Setter
    @ToString
    public static class Cors {
        private String[] allowedOrigins = {CorsConfiguration.ALL};
        private String[] allowedMethods = {Methods.GET_STRING, Methods.POST_STRING, Methods.PUT_STRING, Methods.PATCH_STRING, Methods.DELETE_STRING, Methods.OPTIONS_STRING};
        private String[] allowedHeaders = {CorsConfiguration.ALL};
        private boolean allowedCredentials = true;
        private long maxAge = 3600;
        private String[] exposedHeaders;
    }
}
