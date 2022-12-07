package org.origami.common.core.data.query.impl;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.origami.common.core.data.query.Order;
import org.origami.common.core.data.query.PageQuery;
import org.origami.common.core.exception.IllegalParamException;

import java.util.Collections;
import java.util.List;

/**
 * 分页条件查询的复合条件
 *
 * @author origami
 * @date 2022/1/2 11:28
 */
@Getter
public class PageQueryImpl<T> extends QueryImpl<T> implements PageQuery<T> {
    
    private static final int DEFAULT_PAGE_SIZE = 10;
    
    /**
     * 页码
     */
    @ApiModelProperty(value = "页码")
    
    private final int pageNum;
    /**
     * 每页大小
     */
    @ApiModelProperty(value = "每页数量")
    private final int pageSize;
    
    public PageQueryImpl() {
        this(1, 10, null, Collections.emptyList());
    }
    
    public PageQueryImpl(int pageNum, int pageSize, T condition, List<Order> sort) {
        super(condition, sort);
        
        if (pageNum < 1) {
            throw new IllegalParamException("页码必须大于等于1");
        }
        
        if (pageSize < 1) {
            throw new IllegalParamException("每页显示数量不能为空");
        }
        
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
    
    @SuppressWarnings("rawtypes")
    public static PageQueryImpl of(int pageNum, int pageSize) {
        return new PageQueryImpl<>(pageNum, pageSize, null, Collections.emptyList());
    }
    
    public static <T> PageQueryImpl<T> of(T condition) {
        return new PageQueryImpl<>(1, 10, condition, Collections.emptyList());
    }
    
    public static <T> PageQueryImpl<T> of(int pageNum, int pageSize, T condition) {
        return new PageQueryImpl<>(pageNum, pageSize, condition, Collections.emptyList());
    }
    
    public static <T> PageQueryImpl<T> of(int pageNum, int pageSize,
                                          T condition, List<Order> sort) {
        return new PageQueryImpl<>(pageNum, pageSize, condition, sort);
    }
}
