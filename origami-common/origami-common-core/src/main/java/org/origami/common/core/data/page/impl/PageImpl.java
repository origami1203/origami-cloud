package org.origami.common.core.data.page.impl;

import lombok.Data;
import org.origami.common.core.data.page.Page;
import org.origami.common.core.utils.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Page的默认实现类
 *
 * @author origami
 * @date 2022/1/17 21:40
 */
@Data
public class PageImpl<T> implements Page<T>, Serializable {
    private static final long serialVersionUID = 8095392548687879321L;
    
    private final int pageNum;
    private final int pageSize;
    private final long total;
    private final List<T> content = new ArrayList<>();
    
    private PageImpl(int pageNum, int pageSize, long total, List<T> content) {
        Assert.isTrue(pageNum > 0, "当前页不能小于1");
        Assert.isTrue(pageSize > 0, "每页数量不能小于1");
        Assert.nonNull(content, "列表不能为空");
        
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.content.addAll(content);
    }
    
    public static <T> PageImpl<T> of(int pageNum, int pageSize, long total, List<T> content) {
        return new PageImpl<>(pageNum, pageSize, total, content);
    }
    
    @Override
    public int getPageSize() {
        return this.pageSize;
    }
    
    
    @Override
    public int getPageNum() {
        return this.pageNum;
    }
    
    
    @Override
    public List<T> getContent() {
        return Collections.unmodifiableList(this.content);
    }
    
    @Override
    public long getTotal() {
        return this.total;
    }
    
    
    @Override
    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        return of(pageNum, pageSize, total, getConvertedContent(converter));
    }
    
    private <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {
        
        Assert.nonNull(converter, "Function must not be null!");
        
        return this.content.stream().map(converter).collect(Collectors.toList());
    }
}
