package com.eric.koo.starter.jpa.audit;

import com.eric.koo.starter.jpa.JpaConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = JpaConstant.PROPERTIES_PREFIX_AUDIT)
public class AuditProperties {

    private String systemCode;
}
