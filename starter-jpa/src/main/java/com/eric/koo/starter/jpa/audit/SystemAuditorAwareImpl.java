package com.eric.koo.starter.jpa.audit;

import com.eric.koo.starter.jpa.JpaConstant;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class SystemAuditorAwareImpl implements AuditorAware<String> {

    private final AuditProperties auditProperties;

    SystemAuditorAwareImpl(AuditProperties auditProperties) {
        this.auditProperties = auditProperties;
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(auditProperties.getSystemCode())
                .map(systemCode -> String.format("%s-%s", JpaConstant.SYSTEM, systemCode))
                .or(() -> Optional.of(JpaConstant.SYSTEM));
    }
}
