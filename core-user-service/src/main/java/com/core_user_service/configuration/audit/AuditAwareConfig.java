package com.core_user_service.configuration.audit;

import com.core_user_service.configuration.filter.ApiRequestContextHolder;
import org.springframework.data.domain.AuditorAware;
import org.springframework.util.StringUtils;

import java.util.Optional;

public class AuditAwareConfig implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {

        if (StringUtils.isEmpty(ApiRequestContextHolder.getContext().getAuthId())) {
            return Optional.of("SYSTEM_USER");
        }
        return Optional.of(ApiRequestContextHolder.getContext().getAuthId());

    }
}
