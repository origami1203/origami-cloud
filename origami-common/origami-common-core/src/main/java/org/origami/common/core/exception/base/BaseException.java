package org.origami.common.core.exception.base;

import lombok.Getter;
import org.origami.common.core.base.CodeEnum;

/**
 * 基类Exception
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2021-12-30 13:18
 */
@Getter
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 4253633498393349969L;

    /**
     * 状态码
     */
    protected final String code;
    /**
     * 提示信息
     */
    protected final String message;

    public BaseException() {
        this(CodeEnum.ERROR);
    }

    public BaseException(String message) {
        this(CodeEnum.ERROR.getCode(), message);
    }


    public BaseException(CodeEnum code) {
        this(code.getCode(), code.getMessage());
    }

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

}
