package org.origami.common.core.data.query.impl;

import lombok.Getter;
import org.origami.common.core.data.query.Order;
import org.origami.common.core.data.query.Query;
import org.origami.common.core.utils.BeanUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 查询条件，不分页
 *
 * @author origami
 * @date 2022/1/3 14:04
 */
@Getter
public class QueryImpl<T> implements Query<T> {
    
    /**
     * 查询条件，不为null的字段将使用and组合查询
     */
    protected final T condition;
    /**
     * 排序
     */
    protected final List<Order> sort;
    private static final List<Order> UNSORTED = Collections.emptyList();
    
    public QueryImpl() {
        this(null, null);
    }
    
    public QueryImpl(T condition, List<Order> sort) {
        this.condition = condition;
        if (sort == null) {
            sort = UNSORTED;
        }
        this.sort = sort;
    }
    
    
    @Override
    public Map<String, Object> getConditionMap() {
        return this.condition == null ? Collections.emptyMap() : BeanUtils.beanToMap(condition,
                                                                                     false,
                                                                                     true);
    }
    
    
}
