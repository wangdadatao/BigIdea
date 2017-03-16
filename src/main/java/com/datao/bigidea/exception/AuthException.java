package com.datao.bigidea.exception;

public class AuthException extends RuntimeException {

    private static final long serialVersionUID = -1058946876950167060L;

    public AuthException() {
        super();
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }
}
