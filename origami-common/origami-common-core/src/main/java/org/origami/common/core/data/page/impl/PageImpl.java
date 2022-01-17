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
 * 实现类
 *
 * @author origami
 * @date 2022/1/17 21:40
 */
@Data
public class PageImpl<T> implements Page<T>, Serializable {
    private static final long serialVersionUID = 8095392548687879321L;
    
    private final int current;
    private final int size;
    private final long total;
    private final List<T> content = new ArrayList<>();
    
    public PageImpl(int current, int size, long total, List<T> content) {
        Assert.isTrue(current > 0, "当前页不能小于1");
        Assert.isTrue(size > 0, "每页数量不能小于1");
        Assert.nonNull(content, "列表不能为空");
        this.current = current;
        this.size = size;
        this.total = total;
        this.content.addAll(content);
    }
    
    
    @Override
    public int getSize() {
        return this.size;
    }
    
    
    @Override
    public int getCurrent() {
        return this.current;
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
    public long getOffset() {
        return (current - 1) - size;
    }
    
    @Override
    public boolean hasContent() {
        return !content.isEmpty();
    }
    
    @Override
    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        return new PageImpl<>(current, size, total, getConvertedContent(converter));
    }
    
    private <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {
        
        Assert.nonNull(converter, "Function must not be null!");
        
        return this.content.stream().map(converter).collect(Collectors.toList());
    }
}
