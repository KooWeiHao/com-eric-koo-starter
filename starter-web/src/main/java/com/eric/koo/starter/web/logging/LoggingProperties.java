package com.eric.koo.starter.web.logging;

import com.eric.koo.starter.web.WebConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = WebConstant.PROPERTIES_PREFIX_LOGGING)
public class LoggingProperties {

    private String[] printStreamPackages = {};
}
