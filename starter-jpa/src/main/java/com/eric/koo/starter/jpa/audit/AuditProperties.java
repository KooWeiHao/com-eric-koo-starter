package com.eric.koo.starter.jpa.audit;

import com.eric.koo.starter.jpa.JpaConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = JpaConstant.PROPERTIES_PREFIX_AUDIT)
public class AuditProperties {

    private String systemCode;
}
