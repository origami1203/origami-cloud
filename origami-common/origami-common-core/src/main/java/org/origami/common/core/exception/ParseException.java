package org.origami.common.core.exception;

import org.origami.common.core.exception.base.BaseException;

/**
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-03-27 14:00
 */
public class ParseException extends BaseException {

    public ParseException() {
        super("解析异常");
    }


    public ParseException(String message) {
        super(message);
    }
}
