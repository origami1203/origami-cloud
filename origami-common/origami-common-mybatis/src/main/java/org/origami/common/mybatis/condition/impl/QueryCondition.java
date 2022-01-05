package org.origami.common.mybatis.condition.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.origami.common.core.condition.Condition;
import org.origami.common.mybatis.enums.Direction;

import java.io.Serializable;
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
public class QueryCondition<T> implements Condition<T>, Serializable {
    
    
    @ApiModelProperty(value = "查询条件，不为null的字段将使用and组合查询")
    protected T condition;
    
    @ApiModelProperty(value = "排序")
    protected Map<String, Direction> orders = Maps.newLinkedHashMap();
    
    
    @Override
    public Map<String, Object> getConditionMap() {
        return this.condition == null ? Collections.emptyMap() : BeanUtil.beanToMap(condition,
                                                                                    true,
                                                                                    true);
    }
    
    public List<OrderItem> getOrderList() {
        if (orders.isEmpty()) {
            return Collections.emptyList();
        }
        List<OrderItem> orderItemList = Lists.newArrayList();
        orders.forEach((fieldName, direction) -> {
            boolean asc = !Direction.DESC.equals(direction);
            orderItemList.add(new OrderItem(StrUtil.toUnderlineCase(fieldName), asc));
        });
        
        return orderItemList;
    }
}
