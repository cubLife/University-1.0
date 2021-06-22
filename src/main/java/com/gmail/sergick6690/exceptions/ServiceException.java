package com.gmail.sergick6690.exceptions;

public class ServiceException extends Exception {
    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException() {

    }
}
