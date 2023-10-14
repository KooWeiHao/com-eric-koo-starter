package com.eric.koo.starter.web.mvc;

import com.eric.koo.starter.web.WebConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.UUID;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Configuration
class RequestReferenceIdFilter extends OncePerRequestFilter {

    @Value("#{new java.util.Random()}")
    private Random random;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var requestReferenceId = generateUniqueNumericId(WebConstant.PREFIX_REQUEST_REFERENCE_ID);
        try(var ignored = CloseableThreadContext.push(requestReferenceId)) {
            filterChain.doFilter(request, response);
        }
    }

    // TODO: Move it to util repo
    private String generateUniqueNumericId(String prefix) {
        // Convert UUID to int
        var uuid = UUID.randomUUID();
        var uuidByteArray = ByteBuffer.wrap(new byte[16])
                .putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits())
                .array();
        var uuidInt = ByteBuffer.wrap(uuidByteArray).getInt();

        // Ensure that the ID is always having the size of 10
        var uuidString = StringUtils.rightPad(
                String.valueOf(Math.abs(uuidInt)),
                10,
                String.valueOf(random.nextInt(10))
        );

        return prefix.concat(uuidString);
    }
}
