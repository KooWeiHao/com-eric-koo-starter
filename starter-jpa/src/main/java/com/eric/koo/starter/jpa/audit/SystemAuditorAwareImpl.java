package com.eric.koo.starter.jpa.audit;

import com.eric.koo.starter.util.Constant;
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
                .or(() -> Optional.of(Constant.SYSTEM));
    }
}
