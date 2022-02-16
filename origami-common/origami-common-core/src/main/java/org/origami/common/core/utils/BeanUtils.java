package org.origami.common.core.utils;

import cn.hutool.core.bean.BeanUtil;
import lombok.experimental.UtilityClass;

import java.util.Map;

/**
 * bean工具类
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-01-12 10:11
 */
@UtilityClass
public class BeanUtils {
    
    public <T> T copyProperties(Object source, Class<T> clazz) {
        return BeanUtil.copyProperties(source, clazz);
    }
    
    public void copyProperties(Object source, Object target) {
        BeanUtil.copyProperties(source, target);
    }
    
    
    public Map<String, Object> beanToMap(Object bean) {
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
    public Map<String, Object> beanToMap(Object bean,
                                         boolean camelToUnderscore,
                                         boolean ignoreNullValue) {
        Assert.nonNull(bean, "object不能为空");
        
        return BeanUtil.beanToMap(bean, camelToUnderscore, ignoreNullValue);
    }
    
}
