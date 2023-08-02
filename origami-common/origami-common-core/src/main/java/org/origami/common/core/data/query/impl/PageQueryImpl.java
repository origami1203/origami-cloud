package org.origami.common.core.data.query.impl;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.origami.common.core.data.query.Order;
import org.origami.common.core.data.query.PageQuery;
import org.origami.common.core.exception.IllegalParamException;
import org.origami.common.core.utils.Assert;

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

    public PageQueryImpl(int pageNum, int pageSize, T condition, List<Order> sort) {
        super(condition, sort);

        Assert.isTrue(pageNum >= 1, "页码必须>=1");
        Assert.isTrue(pageSize >= 1, "页面数量必须>=1");

        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @SuppressWarnings("rawtypes")
    public static PageQueryImpl of() {
        return of(1, DEFAULT_PAGE_SIZE);
    }

    public static PageQueryImpl<?> of(int pageNum, int pageSize) {
        return of(pageNum, pageSize, null);
    }

    public static <T> PageQueryImpl<T> of(T condition) {
        return of(1, DEFAULT_PAGE_SIZE, condition, Collections.emptyList());
    }

    public static <T> PageQueryImpl<T> of(int pageNum, int pageSize, T condition) {
        return of(pageNum, pageSize, condition, Collections.emptyList());
    }

    public static <T> PageQueryImpl<T> of(int pageNum, int pageSize,
                                          T condition, List<Order> sort) {
        return new PageQueryImpl<>(pageNum, pageSize, condition, sort);
    }
}
