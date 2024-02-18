package com.eric.koo.starter.web.mvc;

import com.eric.koo.starter.util.UUIDUtil;
import com.eric.koo.starter.web.WebConstant;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Configuration
class RequestReferenceIdFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var requestReferenceId = WebConstant.PREFIX_REQUEST_REFERENCE_ID
                .concat(UUIDUtil.generateShortUUID());
        try(var ignored = CloseableThreadContext.push(requestReferenceId)) {
            filterChain.doFilter(request, response);
        }
    }
}
