package com.br.microservice.geographic.exception;

public class BreakForEachException extends RuntimeException {
    public BreakForEachException() {
        super();
    }

    public BreakForEachException(String message) {
        super(message);
    }

    public BreakForEachException(String message, Throwable cause) {
        super(message, cause);
    }

    public BreakForEachException(Throwable cause) {
        super(cause);
    }

    protected BreakForEachException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
