package com.zzzz.service;

import org.springframework.http.HttpStatus;

public abstract class ServiceException extends Exception {
    private HttpStatus status;
    public HttpStatus getStatus() {
        return status;
    }

    ServiceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
