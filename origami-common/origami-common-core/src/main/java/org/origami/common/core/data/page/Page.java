package org.origami.common.core.data.page;

import java.util.List;
import java.util.function.Function;

/**
 * 分页
 *
 * @author origami
 * @date 2022/1/17 21:20
 */
public interface Page<T> {
    
    /**
     * 获取页面大小
     */
    int getPageSize();
    
    /**
     * 当前页码
     */
    int getPageNum();
    
    
    /**
     * 获取数据内容
     *
     * @return {@code List<T>}
     */
    List<T> getContent();
    
    /**
     * 记录总数
     */
    long getTotal();
    
    /**
     * 总页数
     *
     * @return 总页码
     */
    default int getTotalPages() {
        return getPageSize() == 0 ? 1 : (int) Math.ceil((double) getTotal() / (double) getPageSize());
    }
    
    /**
     * 类型转换
     *
     * @param converter 转换函数
     * @param <U>       转换后类型
     * @return 转换后分页数据
     */
    <U> Page<U> map(Function<? super T, ? extends U> converter);
}
