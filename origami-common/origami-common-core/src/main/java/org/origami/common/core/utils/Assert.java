package org.origami.common.core.utils;

import lombok.experimental.UtilityClass;
import org.origami.common.core.exception.IllegalParamException;

/**
 * 断言工具类
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-01-12 10:15
 */
@UtilityClass
public class Assert {
    /**
     * 断言对象不能为空
     *
     * @param object  被断言对象
     * @param message 出现异常时展示的异常信息
     */
    public void nonNull(Object object, String message) {
        if (object == null) {
            throw new IllegalParamException(message);
        }
    }

    /**
     * 断言对象为空
     *
     * @param object  被断言对象
     * @param message 出现异常时展示的异常信息
     */
    public void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalParamException(message);
        }
    }

    /**
     * 断言为true
     *
     * @param condition 条件
     * @param message   错误提示信息
     */
    public void isTrue(boolean condition, String message) {
        if (!condition) {
            throw new IllegalParamException(message);
        }
    }
}
