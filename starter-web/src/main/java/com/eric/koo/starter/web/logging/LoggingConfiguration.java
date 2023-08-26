package com.eric.koo.starter.web.logging;

import com.eric.koo.starter.web.WebConstants;
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

    private static final String LOG_PROD_XML = "log4j2-prod.xml";

    @Bean
    void configureLog4j2() {
        if(activeProfiles.contains(WebConstants.PROFILE_PROD)) {
            Configurator.initialize(null, ResourceUtils.CLASSPATH_URL_PREFIX + LOG_PROD_XML);
            log.info("Configured log - {}", LOG_PROD_XML);
        }
    }
}
