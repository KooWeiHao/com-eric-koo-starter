package com.eric.koo.starter.jpa.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = JpaAuditingConfiguration.AUDITOR_AWARE, modifyOnCreate = false)
class JpaAuditingConfiguration {

    public static final String AUDITOR_AWARE = "auditorAware";

    private final AuditorAware<String> auditorAware;

    JpaAuditingConfiguration(AuditorAware<String> auditorAware) {
        this.auditorAware = auditorAware;
    }

    @Bean(AUDITOR_AWARE)
    AuditorAware<String> auditorAware() {
        return auditorAware;
    }
}
