package com.eric.koo.starter.web.mvc;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Optional;
import java.util.StringJoiner;

@Log4j2
@Order
@ControllerAdvice
class StandardExceptionHandler extends ResponseEntityExceptionHandler {

    private final StandardExceptionHandlerProperties standardExceptionHandlerProperties;

    StandardExceptionHandler(StandardExceptionHandlerProperties standardExceptionHandlerProperties) {
        this.standardExceptionHandlerProperties = standardExceptionHandlerProperties;
    }

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder){
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleGenericException(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<Object> handleResponseStatusException(ResponseStatusException responseStatusException, WebRequest request) {
        return handleExceptionInternal(responseStatusException, responseStatusException.getReason(), responseStatusException.getStatus(), request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException constraintViolationException, WebRequest request) {
        var firstViolation = constraintViolationException.getConstraintViolations().iterator().next();
        var stringJoiner = new StringJoiner(StringUtils.SPACE);
        Optional.ofNullable(((PathImpl) firstViolation.getPropertyPath()).getLeafNode().getName())
                .ifPresent(stringJoiner::add);
        stringJoiner.add(firstViolation.getMessage());

        return handleExceptionInternal(constraintViolationException, stringJoiner.toString(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException maxUploadSizeExceededException, WebRequest request) {
        return handleExceptionInternal(maxUploadSizeExceededException, null, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception ex, String error, HttpStatus status, WebRequest request) {
        // always log stack trace of exception
        log.error(ex.getMessage(), ex);

        var responseModel = ResponseModelBuilder.failed(error, status);
        return super.handleExceptionInternal(ex, responseModel, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (standardExceptionHandlerProperties.isPrintStackTraceEnabled()) {
            log.error(ex.getMessage(), ex);
        } else {
            log.error(ex.getMessage());
        }

        var responseModel = ResponseModelBuilder.failed(ex.getMessage(), status);
        return super.handleExceptionInternal(ex, responseModel, headers, status, request);
    }
}
