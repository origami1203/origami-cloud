package org.origami.common.jpa.condition.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.sql.Direction;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.origami.common.core.condition.Condition;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 查询条件，不分页
 *
 * @author origami
 * @date 2022/1/3 14:04
 */
@Data
public class QueryCondition<T> implements Condition<T> {
    
    @ApiModelProperty(value = "查询条件，不为null的字段将使用and组合查询")
    protected T condition;
    
    @ApiModelProperty(value = "排序字段")
    protected Map<String, Direction> orders = Maps.newLinkedHashMap();
    
    
    @Override
    public Map<String, Object> getConditionMap() {
        return condition == null ? Collections.emptyMap()
                                 : BeanUtil.beanToMap(condition, false, true);
    }
    
    public Sort getSort() {
        if (orders.isEmpty()) {
            return Sort.unsorted();
        }
        
        List<Sort.Order> orderList = Lists.newArrayList();
        
        orders.forEach((fieldName, direction) -> {
            Sort.Direction jpaDirection = Sort.Direction.DESC;
            if (Direction.ASC.equals(direction)) {
                jpaDirection = Sort.Direction.ASC;
            }
            orderList.add(new Sort.Order(jpaDirection, fieldName));
        });
        
        return Sort.by(orderList);
    }
}
