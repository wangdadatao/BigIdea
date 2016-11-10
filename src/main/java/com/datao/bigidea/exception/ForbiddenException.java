package com.datao.bigidea.exception;


public class ForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 7177549176140059612L;

    public ForbiddenException() {
        super();
    }

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenException(Throwable cause) {
        super(cause);
    }

}
