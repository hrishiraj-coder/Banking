package com.core_user_service.configuration.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
public class AppAuthUserFilter implements Filter {

    private static final String HTTP_HEADER_AUTH_USER_ID = "X-Auth-Id";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String userAuthId = request.getHeader(HTTP_HEADER_AUTH_USER_ID);
        log.info("Incoming request from {}", userAuthId);
        if (!StringUtils.isEmpty(userAuthId)) {
            ApiRequestContextHolder.getContext().setAuthId(userAuthId);
        }

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            ApiRequestContextHolder.clearContext();
        }
    }
}
