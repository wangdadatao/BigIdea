package com.datao.bigidea.utils;


import com.datao.bigidea.exception.ApplicationException;
import com.datao.bigidea.exception.AuthException;
import com.datao.bigidea.exception.ForbiddenException;
import com.datao.bigidea.exception.ParamsException;
import com.github.pagehelper.Page;

import java.util.HashMap;
import java.util.Map;

public class ResEnv<T> {
    private int code;
    private String msg;

    private T content;

    private final Map<String, Object> options = new HashMap<>();

    public static <T> ResEnv<T> success(String msg, T object) {
        return new ResEnv<>(Constants.RES_CODE_OK, msg, object);
    }

    public static <T> ResEnv<T> success(String msg) {
        return new ResEnv<>(Constants.RES_CODE_OK, msg, null);
    }

    public static <T> ResEnv<T> success(T object) {
        return new ResEnv<>(Constants.RES_CODE_OK, Constants.DEF_SUCC_MSG, object);
    }

    public static <T> ResEnv<T> success() {
        return success(Constants.DEF_SUCC_MSG);
    }

    public static <T> ResEnv<T> fail(String msg) {
        return new ResEnv<T>(Constants.RES_CODE_ERROR, msg, null);
    }

    public static <T> ResEnv<T> fail() {
        return fail(Constants.DEF_FAIL_MSG);
    }

    public static <T> ResEnv<T> fail(String msg, Exception e) {
        if (e instanceof AuthException) {
            return new ResEnv<>(Constants.RES_CODE_UNAUTH, e.getMessage(), null);
        }else if (e instanceof ForbiddenException) {
            return new ResEnv<>(Constants.RES_CODE_FORBIDDEN, e.getMessage(), null);
        }else if (e instanceof ParamsException) {
            return new ResEnv<>(Constants.RES_CODE_PARAMS, e.getMessage(), null);
        }else if (e instanceof ApplicationException
                || e instanceof IllegalArgumentException) {
            return new ResEnv<>(Constants.RES_CODE_ERROR, e.getMessage(), null);
        } else {
            return fail(msg);
        }
    }

    public static <T> ResEnv<T> fail(Exception e) {
        return fail(Constants.DEF_FAIL_MSG, e);
    }

    public ResEnv() {
    }

    public ResEnv(int code) {
        this();
        this.code = code;
    }

    public ResEnv(int code, String msg) {
        this(code);
        this.msg = msg;
    }

    public ResEnv(int code, String msg, T content) {

        this(code, msg);
        this.content = content;

        if (content instanceof Page) {
            Page page = (Page) content;
            this.addOption("pageNum", page.getPageNum());
            this.addOption("pageSize", page.getPageSize());
            this.addOption("total", page.getTotal());
        };

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public ResEnv addOption(String key, Object value) {
        options.put(key, value);
        return this;
    }

    public <T> T getOption(String key) {
        return (T) options.get(key);
    }

    public boolean hasOption(String key) {
        return options.containsKey(key);
    }
}
