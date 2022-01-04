package org.origami.common.mybatis.condition.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.origami.common.mybatis.condition.Condition;
import org.origami.common.mybatis.enums.Direction;

import java.io.Serializable;
import java.util.Map;

/**
 * 查询条件，不分页
 *
 * @author origami
 * @date 2022/1/3 14:04
 */
@Data
public class QueryCondition<T> implements Condition<T>, Serializable {
    
    
    @ApiModelProperty(value = "查询条件，不为null的字段将使用and组合查询")
    private T condition;
    
    @JsonIgnore
    private Map<String, Object> params;
    
    private Map<String, Direction> orders = Maps.newLinkedHashMap();
    
    @Override
    public T getConditionEntity() {
        return condition;
    }
}
