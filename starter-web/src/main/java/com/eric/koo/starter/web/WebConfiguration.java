package com.eric.koo.starter.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Log4j2
@Configuration
@ComponentScan(basePackageClasses = WebConfiguration.class)
class WebConfiguration {

    @PostConstruct
    private void init() {
        log.info("{}", "[Starter Web] is enabled");
    }
}
