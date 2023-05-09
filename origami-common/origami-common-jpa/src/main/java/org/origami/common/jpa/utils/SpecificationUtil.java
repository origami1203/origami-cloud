package org.origami.common.jpa.utils;

import cn.hutool.core.collection.CollUtil;
import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import org.origami.common.core.data.query.Condition;
import org.origami.common.core.data.query.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * jpa工具类 spec条件构建工具(默认包含逻辑删除)
 *
 * @author origami
 * @date 2022/1/2 19:02
 */
public abstract class SpecificationUtil {
    
    
    /**
     * 获取spring data jpa的Specification
     *
     * @param condition 条件
     * @return {@code Specification<T>}
     */
    public static <T> Specification<T> getSpecification(Condition<T> condition) {
        if (Objects.isNull(condition)) {
            return null;
        }
        
        Map<String, Object> params = condition.getConditionMap();
        
        PredicateBuilder<T> builder = Specifications.and();
        
        // 添加逻辑删除条件
        builder.eq("deleted", false);
        
        params.forEach((fieldName, value) -> {
            if (value instanceof String) {
                // 只支持最左前缀like查询
                builder.like(fieldName, value + "%");
                return;
            }
            
            builder.eq(fieldName, value);
        });
        
        return builder.build();
    }
    
    /**
     * 获取jpa的Sort排序
     *
     * @param orders orders
     * @return {@link  Sort}
     */
    public static Sort getJpaSort(List<Order> orders) {
        
        if (CollUtil.isEmpty(orders)) {
            return Sort.unsorted();
        }
        
        return Sort.by(orders.stream()
                             .map(it -> new Sort.Order(it.getDirection().isAscending()
                                                       ? Sort.Direction.ASC
                                                       : Sort.Direction.DESC,
                                                       it.getProperty()))
                             .collect(Collectors.toList()));
    }
}
