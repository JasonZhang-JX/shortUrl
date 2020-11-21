package com.zjs.url.api.common;


import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 0);
        put("msg", "success");
    }

    public static R error() {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, msg);
    }

    public static R ok(String Code, String msg) {
        R r = new R();
        r.put("code",Code);
        r.put("msg", msg);
        return r;
    }
    public static R error(String Code, String msg) {
        R r = new R();
        r.put("code",Code);
        r.put("msg", msg);
        return r;
    }

    public static R error(HttpStatus httpStatus, String msg) {
        R r = new R();
        r.put("code", httpStatus.value());
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static R ok() {
        return new R();
    }

}
