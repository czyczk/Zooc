package com.zzzz.controller;

import com.zzzz.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity handleServiceException(ServiceException e) {
        // Log the exception as DEBUG
        logger.debug(e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }

    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity handleSqlException(SQLException e) {
        // Log the error
        logger.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务内部错误。");
    }

    @ExceptionHandler(value = RedisConnectionFailureException.class)
    public ResponseEntity handleRedisConnectionFailureException(RedisConnectionFailureException e) {
        // Log the error
        logger.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("无法连接至 Redis 服务器，请检查网络情况。");
    }

    @ExceptionHandler(value = UnsupportedOperationException.class)
    public ResponseEntity<String> handleUnsupportedOperationException() {
        // Log the error
        logger.error("该操作尚未实现。");
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("该操作尚未实现。");
    }

    @ExceptionHandler(value = IOException.class)
    public ResponseEntity handleIOException(IOException e) {
        // Log the error
        logger.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("I/O 错误。");
    }
}
