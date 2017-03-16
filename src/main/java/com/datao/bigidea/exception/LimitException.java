package com.datao.bigidea.exception;


public class LimitException extends RuntimeException {

    private static final long serialVersionUID = -8450456478891449604L;

    public LimitException() {
        super();
    }

    public LimitException(String message) {
        super(message);
    }

    public LimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public LimitException(Throwable cause) {
        super(cause);
    }
}
