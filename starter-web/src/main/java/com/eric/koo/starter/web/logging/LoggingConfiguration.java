package com.eric.koo.starter.web.logging;

import com.eric.koo.starter.commons.constant.Constant;
import com.eric.koo.starter.web.WebConstant;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.util.List;

@Log4j2
@Configuration
class LoggingConfiguration {

    @Value("${spring.profiles.active:}")
    private List<String> activeProfiles;

    @Bean
    public void configureLog4j2() {
        if(activeProfiles.contains(Constant.ENVIRONMENT_PRODUCTION)) {
            Configurator.initialize(null, ResourceUtils.CLASSPATH_URL_PREFIX + WebConstant.LOG_PROD_XML);
            log.info("Configured log - {}", WebConstant.LOG_PROD_XML);
        }
    }
}
