package com.zjs.url.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 自定义异常
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RcsException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private int code;
    private String msg;

    public RcsException(String msg) {
        super(msg);
        this.msg = msg;
    }

}
