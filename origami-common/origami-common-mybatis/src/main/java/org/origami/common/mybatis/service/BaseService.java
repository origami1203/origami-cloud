package org.origami.common.mybatis.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.origami.common.mybatis.condition.impl.PageQueryCondition;
import org.origami.common.mybatis.condition.impl.QueryCondition;
import org.origami.common.mybatis.entity.BaseEntity;
import org.origami.common.mybatis.utils.WrapperUtil;

import java.util.List;

/**
 * 自定义基类service
 *
 * @author origami
 * @date 2022/1/3 17:00
 */
public interface BaseService<T extends BaseEntity> extends IService<T> {
    
    /**
     * 条件查询列表
     *
     * @param condition
     * @return
     */
    @SuppressWarnings("unchecked")
    default List<T> list(QueryCondition<?> condition) {
        return getBaseMapper().selectList(WrapperUtil.getWrapper(condition));
    }
    
    /**
     * 条件查询分页
     *
     * @param condition
     * @return
     */
    @SuppressWarnings("unchecked")
    default IPage<T> page(PageQueryCondition<?> condition) {
        Page<T> page = new Page<>(condition.getPageNum(),
                                             condition.getPageSize());
        return getBaseMapper().selectPage(page, WrapperUtil.getWrapper(condition));
    }
    
    /**
     * 条件查询计数
     *
     * @param condition
     * @return
     */
    default long count(QueryCondition<?> condition) {
        return getBaseMapper().selectCount(WrapperUtil.getWrapper(condition));
    }
}
