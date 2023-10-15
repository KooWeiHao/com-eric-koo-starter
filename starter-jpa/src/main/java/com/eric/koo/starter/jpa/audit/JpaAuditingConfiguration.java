package com.eric.koo.starter.jpa.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef="auditorAware", modifyOnCreate = false)
class JpaAuditingConfiguration {

    private final AuditorAware<String> auditorAware;

    JpaAuditingConfiguration(AuditorAware<String> auditorAware) {
        this.auditorAware = auditorAware;
    }

    @Bean
    AuditorAware<String> auditorAware() {
        return auditorAware;
    }
}
