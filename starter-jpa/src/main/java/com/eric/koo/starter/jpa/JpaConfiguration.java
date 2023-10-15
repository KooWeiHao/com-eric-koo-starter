package com.eric.koo.starter.jpa;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = JpaConfiguration.class)
class JpaConfiguration {
}
