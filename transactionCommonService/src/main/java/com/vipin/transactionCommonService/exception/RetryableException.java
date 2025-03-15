package com.vipin.transactionCommonService.exception;

public class RetryableException extends RuntimeException {

    public RetryableException(Throwable cause) {
        super(cause);
    }

    public RetryableException(String message) {
        super(message);
    }

}
