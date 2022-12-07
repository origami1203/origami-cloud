package org.origami.common.core.data.query;

/**
 * 分页查询
 *
 * @author origami
 * @date 2022/4/11 23:28
 */
public interface PageQuery<T> extends Query<T> {
    
    /**
     * 获取页码
     *
     * @return 页码
     */
    int getPageNum();
    
    /**
     * 页面大小
     *
     * @return 每页数据大小
     */
    int getPageSize();
}
