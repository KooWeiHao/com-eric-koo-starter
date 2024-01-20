package com.eric.koo.starter.web.mvc;

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
@ConfigurationProperties(prefix = WebConstant.PROPERTIES_PREFIX_API)
public class ApiProperties {

    private boolean performanceMonitorEnabled = true;
}
