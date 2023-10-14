package com.eric.koo.starter.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = WebConfiguration.class)
class WebConfiguration {
}
