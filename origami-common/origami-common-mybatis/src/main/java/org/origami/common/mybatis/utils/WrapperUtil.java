package org.origami.common.mybatis.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import org.origami.common.core.data.query.Order;
import org.origami.common.core.data.query.Query;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author origami
 * @date 2022/1/3 17:37
 */
@UtilityClass
public class WrapperUtil {
    
    @SuppressWarnings("all")
    public QueryWrapper getWrapper(Query<?> condition) {
        return Optional.ofNullable(condition)
                       .map(s -> {
                           Map<String, Object> conditionMap = s.getConditionMap();
                           QueryWrapper queryWrapper = new QueryWrapper();
                           conditionMap.forEach((fieldName, obj) -> {
                               if (obj instanceof String) {
                                   queryWrapper.likeRight(fieldName, obj.toString());
                               } else {
                                   queryWrapper.eq(fieldName, obj);
                               }
                           });
    
    
                           List<OrderItem> orderList = getOrderList(condition);
    
                           orderList.forEach(orderItem -> queryWrapper.orderBy(true,
                                                                               orderItem.isAsc(),
                                                                               orderItem.getColumn()));
                           
                           return queryWrapper;
                       })
                       .orElse(new QueryWrapper());
        
    }
    
    private List<OrderItem> getOrderList(Query<?> query) {
        if (query.getSort().isEmpty()) {
            return Collections.emptyList();
        }
        List<OrderItem> orderItemList = Lists.newArrayList();
        query.getSort().forEach(it -> {
            boolean asc = Order.Direction.ASC.equals(it.getDirection());
            orderItemList.add(new OrderItem(StrUtil.toUnderlineCase(it.getColumn()), asc));
        });

        return orderItemList;
    }
    
}
