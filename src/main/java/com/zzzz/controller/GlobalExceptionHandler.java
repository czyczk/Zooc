package com.zzzz.controller;

import com.zzzz.service.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // TODO logger undefined

    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity handleServiceException(ServiceException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }

    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity handleSqlException() {
        // TODO log the error
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("内部错误。");
    }

    @ExceptionHandler(value = UnsupportedOperationException.class)
    public ResponseEntity<String> handleUnsupportedOperationException() {
        // TODO log the error
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("该操作尚未实现。");
    }
}
