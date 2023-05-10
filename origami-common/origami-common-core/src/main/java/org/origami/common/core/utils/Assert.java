package org.origami.common.core.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import org.origami.common.core.exception.IllegalParamException;

import java.util.Collection;
import java.util.Map;

/**
 * 断言工具类
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-01-12 10:15
 */
public abstract class Assert {
    
    private Assert() {
        throw new UnsupportedOperationException(
                "This is a utility class and cannot be instantiated");
    }
    
    /**
     * 断言对象不能为空
     *
     * @param object 被断言对象
     */
    public static void nonNull(Object object) {
        nonNull(object, "对象不能为空");
    }
    
    /**
     * 断言对象不能为空
     *
     * @param object  被断言对象
     * @param message 出现异常时展示的异常信息
     */
    public static void nonNull(Object object, String message) {
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
    public static void isNull(Object object, String message) {
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
    public static void isTrue(boolean condition, String message) {
        if (!condition) {
            throw new IllegalParamException(message);
        }
    }
    
    /**
     * 集合非空
     *
     * @param collection 集合
     * @param message    错误提示信息
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (CollUtil.isEmpty(collection)) {
            throw new IllegalParamException(message);
        }
    }
    
    /**
     * map非空
     *
     * @param map     map
     * @param message 错误提示信息
     */
    public static void notEmpty(Map<?, ?> map, String message) {
        if (MapUtil.isEmpty(map)) {
            throw new IllegalParamException(message);
        }
    }
    
}
