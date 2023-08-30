package com.eric.koo.starter.web.controller;

import org.springframework.http.HttpStatus;

public interface ResponseModelBuilder {
    <T> ResponseModel<T> success(T data);
    <T> ResponseModel<T> success(T data, String message);
    <T> ResponseModel<T> success(T data, HttpStatus httpStatus);
    <T> ResponseModel<T> success(T data, HttpStatus httpStatus, String message);

    ResponseModel<Void> failed(String error);
    ResponseModel<Void> failed(String error, String message);
    ResponseModel<Void> failed(String error, HttpStatus httpStatus);
    ResponseModel<Void> failed(String error, HttpStatus httpStatus, String message);
}
