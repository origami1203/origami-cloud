package org.origami.common.core.exception;

import org.origami.common.core.exception.base.BaseException;

/**
 * 非法参数异常,使用自定义异常类,方便全局异常处理
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-01-12 10:19
 */
public class IllegalParamException extends BaseException {

    public IllegalParamException() {
        super("非法参数异常");
    }

    public IllegalParamException(String message) {
        super(message);
    }

}
