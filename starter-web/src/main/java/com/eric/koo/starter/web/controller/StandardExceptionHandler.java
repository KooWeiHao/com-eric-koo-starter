package com.eric.koo.starter.web.controller;

import com.eric.koo.starter.web.WebProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@Order
@ControllerAdvice
class StandardExceptionHandler extends ResponseEntityExceptionHandler {

    private final WebProperties webProperties;

    StandardExceptionHandler(WebProperties webProperties) {
        this.webProperties = webProperties;
    }

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder){
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (webProperties.getStandardExceptionHandler().isPrintStackTraceEnabled()) {
            log.error(ex.getMessage(), ex);
        } else {
            log.error(ex.getMessage());
        }

        var responseModel = ResponseModelBuilder.failed(ex.getMessage(), status);
        return super.handleExceptionInternal(ex, responseModel, headers, status, request);
    }
}
