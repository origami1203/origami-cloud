package org.origami.common.mybatis.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import lombok.experimental.UtilityClass;
import org.origami.common.mybatis.condition.impl.QueryCondition;

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
    public QueryWrapper getWrapper(QueryCondition<?> condition) {
        return Optional.ofNullable(condition)
                       .map(s -> {
                           Map<String, Object> conditionMap = s.getConditionMap();
                           QueryWrapper queryWrapper = new QueryWrapper();
                           conditionMap.forEach((fieldName, obj) -> {
                
                               if (obj instanceof String) {
                                   queryWrapper.likeLeft(fieldName, obj.toString());
                               } else {
                                   queryWrapper.eq(fieldName, obj);
                               }
                           });
    
                           List<OrderItem> orderList = s.getOrderList();
    
                           orderList.forEach(orderItem -> queryWrapper.orderBy(true,
                                                                               orderItem.isAsc(),
                                                                               orderItem.getColumn()));
                           
                           return queryWrapper;
                       })
                       .orElse(new QueryWrapper());
        
    }
    
}
