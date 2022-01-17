package org.origami.common.core.exception.base;

import lombok.Getter;
import org.origami.common.core.base.Code;

import java.util.Arrays;

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
     * 异常的位置
     */
    private final String location;
    /**
     * 状态码
     */
    private final String code;
    /**
     * 提示信息
     */
    private final String message;

    public BaseException() {
        this(Code.ERROR);
    }

    public BaseException(String message) {
        this(Code.ERROR.getCode(), message);
    }


    public BaseException(Code code) {
        this(code.getCode(), code.getMessage());
    }

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.location = getExceptionDetail();
    }

    private String getExceptionDetail() {
        return Arrays.stream(this.getStackTrace()).findFirst()
                .map(s -> "location: " + s.getClassName() + "#" + s.getMethodName() + "()方法->" + s.getLineNumber() + "行")
                .orElse("null");
    }

}
