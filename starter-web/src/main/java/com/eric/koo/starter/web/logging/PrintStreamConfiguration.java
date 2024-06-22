package com.eric.koo.starter.web.logging;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Log4j2
@ConditionalOnPrintStream
@Configuration
class PrintStreamConfiguration {

    private final LoggingProperties loggingProperties;

    PrintStreamConfiguration(LoggingProperties loggingProperties) {
        this.loggingProperties = loggingProperties;
    }

    @PostConstruct
    private void initPrintStream() {
        log.debug(loggingProperties);

        var packageList = Arrays.asList(loggingProperties.getPrintStreamPackages());
        System.setOut(new DelegatingPrintStream(packageList));
    }
}
