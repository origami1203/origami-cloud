package org.origami.common.core.utils;

import java.util.Collection;
import java.util.Map;

import org.origami.common.core.exception.IllegalParamException;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 断言工具类
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-01-12 10:15
 */
public abstract class Assert {

    private Assert() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * 断言对象不能为空
     *
     * @param object 被断言对象
     */
    @Deprecated
    public static void nonNull(Object object) {
        nonNull(object, "对象不能为空");
    }

    /**
     * 断言对象不能为空，否则抛出异常
     *
     * @param object 被断言对象
     * @param message 出现异常时展示的异常信息
     */
    public static void nonNull(Object object, String message) {
        if (object == null) {
            throw new IllegalParamException(message);
        }
    }

    /**
     * 断言对象为空，否则抛出异常
     *
     * @param object 被断言对象
     * @param message 出现异常时展示的异常信息
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalParamException(message);
        }
    }

    /**
     * 断言条件为true，否则抛出异常
     *
     * @param condition 条件
     * @param message 错误提示信息
     */
    public static void isTrue(boolean condition, String message) {
        if (!condition) {
            throw new IllegalParamException(message);
        }
    }

    /**
     * 断言集合非空，否则抛出异常
     *
     * @param collection 集合
     * @param message 错误提示信息
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (CollUtil.isEmpty(collection)) {
            throw new IllegalParamException(message);
        }
    }

    /**
     * 断言集合为空，否则抛出异常
     *
     * @param collection 集合
     * @param message 消息
     */
    public static void isEmpty(Collection<?> collection, String message) {
        if (CollUtil.isNotEmpty(collection)) {
            throw new IllegalParamException(message);
        }
    }

    /**
     * 断言map非空，否则抛出异常
     *
     * @param map map
     * @param message 错误提示信息
     */
    public static void notEmpty(Map<?, ?> map, String message) {
        if (MapUtil.isEmpty(map)) {
            throw new IllegalParamException(message);
        }
    }

    /**
     * 断言map为空，否则抛出异常
     *
     * @param map 地图
     * @param message 消息
     */
    public static void isEmpty(Map<?, ?> map, String message) {
        if (MapUtil.isNotEmpty(map)) {
            throw new IllegalParamException(message);
        }
    }

    /**
     * 所给字符串必须非空(不能为null或"")，否则抛出异常
     *
     * @param text 文本
     * @param message 消息
     */
    public static void notEmpty(String text, String message) {
        if (StrUtil.isEmpty(text)) {
            throw new IllegalParamException(message);
        }
    }

    /**
     * 所给字符串必须非空且非空白字符(不能为null或""或是空白字符)，否则抛出异常
     *
     * @param text 文本
     * @param message 消息
     */
    public static void notBlank(String text, String message) {
        if (StrUtil.isBlank(text)) {
            throw new IllegalParamException(message);
        }
    }

    /**
     * 断言数组没有null元素,否则抛出异常
     *
     * @param array 数组
     * @param message 消息
     */
    public static void noNullElements(Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new IllegalParamException(message);
                }
            }
        }
    }

    /**
     * 断言集合没有null元素，否则抛出异常
     *
     * @param collections 集合
     * @param message 消息
     */
    public static void noNullElements(Collection<?>[] collections, String message) {
        if (collections != null) {
            for (Object element : collections) {
                if (element == null) {
                    throw new IllegalParamException(message);
                }
            }
        }
    }

}
