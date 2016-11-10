package com.datao.bigidea.exception;

/**
 * Created by 海涛 on 2016/10/16.
 */
public class ParamsException extends RuntimeException  {

    public ParamsException() {
        super();
    }

    public ParamsException(String message) {
        super(message);
    }

    public ParamsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamsException(Throwable cause) {
        super(cause);
    }

}
