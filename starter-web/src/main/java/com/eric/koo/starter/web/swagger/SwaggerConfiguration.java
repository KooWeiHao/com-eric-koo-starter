package com.eric.koo.starter.web.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

// TODO: Haven't try the security part
@Log4j2
@ConditionalOnSwagger
@Configuration
class SwaggerConfiguration {

    @Value("${springdoc.api-docs.path:/v3/api-docs}")
    private String apiDocsPath;

    @Value("${springdoc.swagger-ui.enabled:true}")
    private boolean swaggerUiEnabled;

    @Value("${springdoc.swagger-ui.path:/swagger-ui/index.html}")
    private String swaggerUiPath;

    private final SwaggerProperties swaggerProperties;

    SwaggerConfiguration(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    @PostConstruct
    private void init() {
        log.info("Swagger Api Docs - {}", apiDocsPath);
        if(swaggerUiEnabled) {
            log.info("Swagger UI - {}", swaggerUiPath);
        }
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(swaggerProperties.getInfo());
    }
}
