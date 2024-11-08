package com.vk.ms.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @Getter
    static class Error {
        private final String reason;
        private final String message;

        Error(String reason, String message) {
            this.message = message;
            this.reason = reason;
        }
    }

    @ExceptionHandler({InvalidDataException.class, HttpMessageNotReadableException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleInvalidDataException(Throwable t) {
        log.warn("Invalid data exception occurred: {}", t.getMessage());
        return new Error(HttpStatus.BAD_REQUEST.getReasonPhrase(), t.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleNotFoundException(NotFoundException ex) {
        log.warn("Not found exception occurred: {}", ex.getMessage());
        return new Error(HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleUnknownException(Exception ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage());
        return new Error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "An unexpected error occurred. Please try again later.");
    }
}
