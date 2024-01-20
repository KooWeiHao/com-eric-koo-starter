package com.eric.koo.starter.web.mvc;

import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;

class ApiPerformanceMonitorInterceptor extends PerformanceMonitorInterceptor {

    @Override
    public void setUseDynamicLogger(boolean useDynamicLogger) {
        super.setUseDynamicLogger(false);
    }

    @Override
    protected boolean isLogEnabled(Log logger) {
        return true;
    }
}
