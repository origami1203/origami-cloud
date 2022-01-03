package org.origami.common.jpa.condition;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Map;

/**
 * 分页条件查询的复合条件
 *
 * @author origami
 * @date 2022/1/2 11:28
 */
@Data
@Accessors(chain = true)
public class PageQueryCondition<T> implements Condition<T>, Serializable {
    
    private static final int DEFAULT_PAGE_SIZE = 10;
    
    /**
     * 页码
     */
    @ApiModelProperty(value = "页码")
    private Integer pageNum = 0;
    /**
     * 每页大小
     */
    @ApiModelProperty(value = "每页数量")
    private Integer pageSize;
    /**
     * 查询条件
     */
    @ApiModelProperty(value = "查询条件，不为null的字段将使用and组合查询")
    private T condition;
    @JsonIgnore
    private Map<String, Object> params;
    private Map<String, Sort.Direction> orders = Maps.newLinkedHashMap();
    
    @Override
    public T getConditionEntity() {
        return this.condition;
    }
    
    /**
     * jpa 分页以0开始
     *
     * @return 小于1抛异常
     */
    @SuppressWarnings("rawtypes")
    public PageQueryCondition setPageNum(Integer pageNum) {
        
        if (pageNum == null) {
            throw new IllegalArgumentException("页码不能为空");
        }
        
        if (pageNum < 1) {
            throw new IllegalArgumentException("页码必须大于等于1");
        }
        
        this.pageNum = pageNum - 1;
        
        return this;
    }
    
    /**
     * 默认页面大小10条
     *
     * @param pageSize
     * @return
     */
    @SuppressWarnings("rawtypes")
    public PageQueryCondition setPageSize(Integer pageSize) {
        
        if (pageSize == null) {
            this.pageSize = DEFAULT_PAGE_SIZE;
            return this;
        }
        
        if (pageSize < 1) {
            throw new IllegalArgumentException("每页显示数量不能为空");
        }
        
        this.pageSize = pageSize;
        
        return this;
    }
    
}
