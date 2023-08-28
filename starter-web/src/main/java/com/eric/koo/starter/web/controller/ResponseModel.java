package com.eric.koo.starter.web.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
public class ResponseModel<T> {
    private Integer code;
    private Status status;
    private String message;
    private T data;
    private String error;
    private SystemInformation systemInformation;

    @Data
    public static class SystemInformation {
        private String referenceId;
        private String path;
        private LocalDateTime timestamp;
    }

    @AllArgsConstructor
    public enum Status {
        SUCCESS("success"),
        FAILED("failed");

        private final String status;

        @JsonValue
        public String getStatus() {
            return status;
        }
    }
}
