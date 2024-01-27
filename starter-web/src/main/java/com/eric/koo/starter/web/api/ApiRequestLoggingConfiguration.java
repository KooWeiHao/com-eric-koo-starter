package com.eric.koo.starter.web.api;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.AbstractRequestLoggingFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@ConditionalOnApiRequestLogging
@Configuration
class ApiRequestLoggingConfiguration {

    private final ApiProperties.ApiLoggingProperties apiLoggingProperties;

    ApiRequestLoggingConfiguration(ApiProperties apiProperties) {
        this.apiLoggingProperties = apiProperties.getLogging();
    }

    @Bean
    public AbstractRequestLoggingFilter apiRequestLoggingFilter() {
        var loggingFilter = new ApiRequestLoggingFilter();

        // Non-configurable
        loggingFilter.setAfterMessagePrefix("Request [");
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);

        // Configurable
        loggingFilter.setIncludeClientInfo(apiLoggingProperties.getRequest().isClientInfoEnabled());
        loggingFilter.setIncludeHeaders(apiLoggingProperties.getRequest().isHeadersEnabled());
        loggingFilter.setMaxPayloadLength(apiLoggingProperties.getMaxPayloadLength());
        loggingFilter.setHeaderPredicate(header ->
                Arrays.stream(apiLoggingProperties.getRequest().getMaskedHeaders())
                        .noneMatch(header::equalsIgnoreCase)
        );

        return loggingFilter;
    }

    private class ApiRequestLoggingFilter extends AbstractRequestLoggingFilter {

        private final Logger log = LogManager.getLogger(ApiRequestLoggingFilter.class);

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            var responseWrapper = new ContentCachingResponseWrapper(response);
            super.doFilterInternal(request, responseWrapper, filterChain);
            logResponse(responseWrapper);
            responseWrapper.copyBodyToResponse();
        }

        @Override
        protected void beforeRequest(HttpServletRequest request, String message) {
            log.debug(
                    "Running Request [{} {}]",
                    request.getMethod(),
                    request.getRequestURI()
            );
        }

        @Override
        protected void afterRequest(HttpServletRequest request, String message) {
            log.debug(message);
        }

        @Override
        protected String getMessagePayload(HttpServletRequest request) {
            var messagePayload = super.getMessagePayload(request);

            return StringUtils.normalizeSpace(messagePayload);
        }

        private void logResponse(HttpServletResponse response) {
            // Get response payload
            var responsePayload = new AtomicReference<String>();
            var responseWrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
            Optional.ofNullable(responseWrapper)
                    .ifPresent(wrapper -> {
                        var buf = wrapper.getContentAsByteArray();
                        if (buf.length > 0) {
                            var length = Math.min(buf.length, getMaxPayloadLength());
                            try {
                                var payload = new String(buf, 0, length, wrapper.getCharacterEncoding());
                                responsePayload.set(StringUtils.normalizeSpace(payload));
                            } catch (UnsupportedEncodingException ex) {
                                responsePayload.set("[unknown]");
                            }
                        }
                    });

            // Build response
            var responseBuilder = new StringBuilder();
            responseBuilder.append("Response [");
            responseBuilder.append(response.getStatus());
            if (apiLoggingProperties.getResponse().isContentTypeEnabled()) {
                responseBuilder.append(" contentType=");
                responseBuilder.append(response.getContentType());
                responseBuilder.append(',');
            }
            responseBuilder.append(" payload=");
            responseBuilder.append(responsePayload.get());
            responseBuilder.append(']');

            log.debug("{}", responseBuilder);
        }
    }
}