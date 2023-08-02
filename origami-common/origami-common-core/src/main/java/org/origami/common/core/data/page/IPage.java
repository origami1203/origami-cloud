package org.origami.common.core.data.page;

import java.util.List;
import java.util.function.Function;

/**
 * page 分页接口
 *
 * @author origami
 * @date 2022/1/17 21:20
 */
public interface IPage<T> {

    /**
     * 获取每页大小
     *
     * @return int
     */
    int getPageSize();

    /**
     * 获取当前页码
     *
     * @return int
     */
    int getPageNum();

    /**
     * 获取数据内容
     *
     * @return {@code List<T>}
     */
    List<T> getContent();

    /**
     * 获取总记录数
     *
     * @return long
     */
    long getTotal();

    /**
     * 获取总页数
     *
     * @return 总页数
     */
    default int getTotalPages() {
        return getPageSize() == 0 ? 1 : (int)Math.ceil((double)getTotal() / (double)getPageSize());
    }

    /**
     * 类型转换
     *
     * @param converter 转换函数
     * @param <U> 转换后类型
     * @return 转换后分页数据
     */
    <U> IPage<U> map(Function<? super T, ? extends U> converter);
}
