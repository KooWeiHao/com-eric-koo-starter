package com.eric.koo.starter.web.swagger;

import com.eric.koo.starter.web.WebConstant;
import io.swagger.v3.oas.models.info.Info;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ToString
@ConditionalOnSwagger
@Configuration
@ConfigurationProperties(prefix = WebConstant.PROPERTIES_PREFIX_SWAGGER)
public class SwaggerProperties {
    private Info info = new Info();
}
