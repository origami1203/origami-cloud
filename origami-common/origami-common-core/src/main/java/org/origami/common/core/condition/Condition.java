package org.origami.common.core.condition;

import cn.hutool.db.sql.Direction;

import java.util.Map;

/**
 * 数据库条件查询接口
 *
 * @author origami
 * @date 2022/1/4 20:35
 */
public interface Condition<T> {
    
    
    /**
     * 获取条件，key为字段名，value为对应的值
     *
     * @return map
     */
    Map<String, Object> getConditionMap();
}
