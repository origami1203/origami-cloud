package org.origami.common.core.data.query;

import java.util.Map;

/**
 * 查询条件
 *
 * @author origami
 * @date 2022/1/4 20:35
 */
public interface Condition<T> {

    /**
     * 获取原始条件bean
     *
     * @return T
     */
    T getCondition();


    /**
     * 获取条件，key为字段名，value为对应的值
     *
     * @return {@code Map<String, Object>}
     */
    Map<String, Object> getConditionMap();
}
