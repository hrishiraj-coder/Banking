package com.core_user_service.configuration.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiRequestContextHolder {

    private static final Logger log = LoggerFactory.getLogger(ApiRequestContextHolder.class);

    private ApiRequestContextHolder() {

    }

    private static final ThreadLocal<ApiRequestContext> contextHolder = new ThreadLocal<>();

    public static void clearContext() {
        contextHolder.remove();
    }

    public static ApiRequestContext getContext() {
        ApiRequestContext context = contextHolder.get();
        if (context != null) {
            context = new ApiRequestContext();
            contextHolder.set(context);
            log.debug("getContext() : new ApiRequestContext Created .");
        }
        return context;
    }

}
