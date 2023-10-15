package com.eric.koo.starter.web.mvc;

import com.eric.koo.starter.web.WebConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Optional;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseModelBuilder {

    private static Optional<String> getCurrentRequestPath() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance)
                .map(requestAttributes -> ((ServletRequestAttributes) requestAttributes).getRequest().getServletPath());
    }

    private static ResponseModel.SystemInformation initSystemInformation() {
        var systemInformation = new ResponseModel.SystemInformation();
        systemInformation.setReferenceId(ThreadContext.peek());
        systemInformation.setTimestamp(LocalDateTime.now());
        getCurrentRequestPath().ifPresentOrElse(
                systemInformation::setPath,
                () -> log.error(WebConstant.ERROR_UNAVAILABLE_REQUEST_PATH)
        );

        return systemInformation;
    }

    public static <T> ResponseModel<T> success(T data) {
        var responseModel = new ResponseModel<T>();
        responseModel.setCode(HttpStatus.OK.value());
        responseModel.setStatus(ResponseModel.Status.SUCCESS);
        responseModel.setMessage(HttpStatus.OK.getReasonPhrase());
        responseModel.setData(data);
        responseModel.setSystemInformation(initSystemInformation());

        return responseModel;
    }

    public static <T> ResponseModel<T> success(T data, String message) {
        var responseModel = new ResponseModel<T>();
        responseModel.setCode(HttpStatus.OK.value());
        responseModel.setStatus(ResponseModel.Status.SUCCESS);
        responseModel.setMessage(message);
        responseModel.setData(data);
        responseModel.setSystemInformation(initSystemInformation());

        return responseModel;
    }

    public static <T> ResponseModel<T> success(T data, HttpStatus httpStatus) {
        var responseModel = new ResponseModel<T>();
        responseModel.setCode(httpStatus.value());
        responseModel.setStatus(ResponseModel.Status.SUCCESS);
        responseModel.setMessage(httpStatus.getReasonPhrase());
        responseModel.setData(data);
        responseModel.setSystemInformation(initSystemInformation());

        return responseModel;
    }

    public static <T> ResponseModel<T> success(T data, HttpStatus httpStatus, String message) {
        var responseModel = new ResponseModel<T>();
        responseModel.setCode(httpStatus.value());
        responseModel.setStatus(ResponseModel.Status.SUCCESS);
        responseModel.setMessage(message);
        responseModel.setData(data);
        responseModel.setSystemInformation(initSystemInformation());

        return responseModel;
    }

    public static ResponseModel<Void> failed(String error) {
        var responseModel = new ResponseModel<Void>();
        responseModel.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setStatus(ResponseModel.Status.FAILED);
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.setError(error);
        responseModel.setSystemInformation(initSystemInformation());

        return responseModel;
    }

    public static ResponseModel<Void> failed(String error, String message) {
        var responseModel = new ResponseModel<Void>();
        responseModel.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setStatus(ResponseModel.Status.FAILED);
        responseModel.setMessage(message);
        responseModel.setError(error);
        responseModel.setSystemInformation(initSystemInformation());

        return responseModel;
    }

    public static ResponseModel<Void> failed(String error, HttpStatus httpStatus) {
        var responseModel = new ResponseModel<Void>();
        responseModel.setCode(httpStatus.value());
        responseModel.setStatus(ResponseModel.Status.FAILED);
        responseModel.setMessage(httpStatus.getReasonPhrase());
        responseModel.setError(error);
        responseModel.setSystemInformation(initSystemInformation());

        return responseModel;
    }

    public static ResponseModel<Void> failed(String error, HttpStatus httpStatus, String message) {
        var responseModel = new ResponseModel<Void>();
        responseModel.setCode(httpStatus.value());
        responseModel.setStatus(ResponseModel.Status.FAILED);
        responseModel.setMessage(message);
        responseModel.setError(error);
        responseModel.setSystemInformation(initSystemInformation());

        return responseModel;
    }
}
