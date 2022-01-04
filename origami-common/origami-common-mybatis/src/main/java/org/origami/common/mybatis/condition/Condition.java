package org.origami.common.mybatis.condition;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.origami.common.mybatis.enums.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    default List<OrderItem> getOrders(Map<String, Direction> orders) {
        if (Objects.isNull(orders) || orders.isEmpty()) {
            return Collections.emptyList();
        }
        
        List<OrderItem> orderList = new ArrayList<>();
        
        orders.forEach((fieldName, direction) -> {
            boolean asc = false;
            if (Direction.ASC.equals(direction)) {
                asc = true;
            }
            orderList.add(new OrderItem(fieldName, asc));
        });
        
        return orderList;
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
