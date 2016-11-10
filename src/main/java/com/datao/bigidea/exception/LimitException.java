package com.datao.bigidea.exception;

/**
 * Created by 海涛 on 2016/8/8.
 */
public class LimitException extends RuntimeException {
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
