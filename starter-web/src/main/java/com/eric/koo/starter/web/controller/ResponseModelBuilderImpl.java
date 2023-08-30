package com.eric.koo.starter.web.controller;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
class ResponseModelBuilderImpl implements ResponseModelBuilder{

    private final HttpServletRequest httpServletRequest;

    ResponseModelBuilderImpl(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    private ResponseModel.SystemInformation initSystemInformation() {
        var systemInformation = new ResponseModel.SystemInformation();
        systemInformation.setReferenceId(ThreadContext.peek());
        systemInformation.setPath(httpServletRequest.getServletPath());
        systemInformation.setTimestamp(LocalDateTime.now());

        return systemInformation;
    }

    @Override
    public <T> ResponseModel<T> success(T data) {
        var responseModel = new ResponseModel<T>();
        responseModel.setCode(HttpStatus.OK.value());
        responseModel.setStatus(ResponseModel.Status.SUCCESS);
        responseModel.setMessage(HttpStatus.OK.getReasonPhrase());
        responseModel.setData(data);
        responseModel.setSystemInformation(initSystemInformation());

        return responseModel;
    }

    @Override
    public <T> ResponseModel<T> success(T data, String message) {
        var responseModel = new ResponseModel<T>();
        responseModel.setCode(HttpStatus.OK.value());
        responseModel.setStatus(ResponseModel.Status.SUCCESS);
        responseModel.setMessage(message);
        responseModel.setData(data);
        responseModel.setSystemInformation(initSystemInformation());

        return responseModel;
    }

    @Override
    public <T> ResponseModel<T> success(T data, HttpStatus httpStatus) {
        var responseModel = new ResponseModel<T>();
        responseModel.setCode(httpStatus.value());
        responseModel.setStatus(ResponseModel.Status.SUCCESS);
        responseModel.setMessage(httpStatus.getReasonPhrase());
        responseModel.setData(data);
        responseModel.setSystemInformation(initSystemInformation());

        return responseModel;
    }

    @Override
    public <T> ResponseModel<T> success(T data, HttpStatus httpStatus, String message) {
        var responseModel = new ResponseModel<T>();
        responseModel.setCode(httpStatus.value());
        responseModel.setStatus(ResponseModel.Status.SUCCESS);
        responseModel.setMessage(message);
        responseModel.setData(data);
        responseModel.setSystemInformation(initSystemInformation());

        return responseModel;
    }

    @Override
    public ResponseModel<Void> failed(String error) {
        var responseModel = new ResponseModel<Void>();
        responseModel.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setStatus(ResponseModel.Status.FAILED);
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.setError(error);
        responseModel.setSystemInformation(initSystemInformation());

        return responseModel;
    }

    @Override
    public ResponseModel<Void> failed(String error, String message) {
        var responseModel = new ResponseModel<Void>();
        responseModel.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setStatus(ResponseModel.Status.FAILED);
        responseModel.setMessage(message);
        responseModel.setError(error);
        responseModel.setSystemInformation(initSystemInformation());

        return responseModel;
    }

    @Override
    public ResponseModel<Void> failed(String error, HttpStatus httpStatus) {
        var responseModel = new ResponseModel<Void>();
        responseModel.setCode(httpStatus.value());
        responseModel.setStatus(ResponseModel.Status.FAILED);
        responseModel.setMessage(httpStatus.getReasonPhrase());
        responseModel.setError(error);
        responseModel.setSystemInformation(initSystemInformation());

        return responseModel;
    }

    @Override
    public ResponseModel<Void> failed(String error, HttpStatus httpStatus, String message) {
        var responseModel = new ResponseModel<Void>();
        responseModel.setCode(httpStatus.value());
        responseModel.setStatus(ResponseModel.Status.FAILED);
        responseModel.setMessage(message);
        responseModel.setError(error);
        responseModel.setSystemInformation(initSystemInformation());

        return responseModel;
    }
}
