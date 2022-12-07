package org.origami.common.core.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

import java.util.Map;

/**
 * BeanUtils工具类,封装了hutool的BeanUtil工具类
 *
 * @author origami
 * @version 1.0.0
 * @date 2022-01-12 10:11
 * @see BeanUtil
 */
public abstract class BeanUtils {
    
    private BeanUtils() {
        throw new UnsupportedOperationException(
                "This is a utility class and cannot be instantiated");
    }
    
    /**
     * 复制属性
     *
     * @param source           源java bean
     * @param clazz            目标类
     * @param ignoreProperties 要忽略属性的字段名的列表
     * @return {@code T}
     */
    public static <T> T copyProperties(Object source,
                                       Class<T> clazz,
                                       String... ignoreProperties) {
        return BeanUtil.copyProperties(source, clazz, ignoreProperties);
    }
    
    /**
     * 复制属性
     *
     * @param source           源java bean
     * @param target           目标java bean
     * @param ignoreProperties 要忽略属性的字段名的列表
     */
    public static void copyProperties(Object source,
                                      Object target,
                                      String... ignoreProperties) {
        BeanUtil.copyProperties(source, target, ignoreProperties);
    }
    
    
    /**
     * bean转map
     *
     * @param bean bean
     * @return {@code Map<String, Object>}
     */
    public static Map<String, Object> beanToMap(Object bean) {
        return beanToMap(bean, false, false);
    }
    
    /**
     * bean转map
     *
     * @param bean              bean
     * @param camelToUnderscore 是否驼峰转下划线
     * @param ignoreNullValue   是否忽略null值
     * @return bean为null会抛出异常
     */
    public static Map<String, Object> beanToMap(Object bean,
                                                boolean camelToUnderscore,
                                                boolean ignoreNullValue) {
        Assert.nonNull(bean, "bean不能为空");
        
        return BeanUtil.beanToMap(bean, camelToUnderscore, ignoreNullValue);
    }
    
    /**
     * map转bean
     *
     * @param map              map
     * @param beanClass        bean的类
     * @param ignoreProperties 要忽略属性
     * @return {@code T}
     */
    public static <T> T mapToBean(Map<?, ?> map,
                                  Class<T> beanClass,
                                  String... ignoreProperties) {
        return BeanUtil.mapToBean(map, beanClass, false,
                                  CopyOptions.create().setIgnoreProperties(ignoreProperties));
    }
    
}
