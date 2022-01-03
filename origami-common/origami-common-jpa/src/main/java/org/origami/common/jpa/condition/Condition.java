package org.origami.common.jpa.condition;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 条件查询
 *
 * @author origami
 * @date 2022/1/3 13:57
 */
public interface Condition<T> {
    /**
     * 获取排序
     *
     * @param orders
     * @return
     */
    default Sort getSort(Map<String, Sort.Direction> orders) {
        if (Objects.isNull(orders) || orders.isEmpty()) {
            return Sort.unsorted();
        }
        
        List<Sort.Order> orderList = new ArrayList<>();
        
        orders.forEach((fieldName, direction) -> {
            orderList.add(new Sort.Order(Optional.ofNullable(direction)
                                                 .orElse(Sort.Direction.DESC), fieldName));
        });
        
        return Sort.by(orderList);
    }
    
    /**
     * 获取条件
     *
     * @param condition
     * @return key为字段名，value为指定值
     */
    default Map<String, Object> getConditionMap(T condition) {
        return condition == null ? Collections.emptyMap()
                                 : BeanUtil.beanToMap(condition, false, true);
    }
    
    T getConditionEntity();
}
