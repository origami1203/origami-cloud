package org.origami.common.mybatis.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.origami.common.core.data.page.Page;
import org.origami.common.core.data.page.impl.PageImpl;
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
    default Page<T> page(PageQueryCondition<?> condition) {
        PageDTO<T> pageDTO =
                new PageDTO<>(condition.getPageNum(), condition.getPageSize());
        IPage<T> iPage = getBaseMapper().selectPage(pageDTO,
                                                    WrapperUtil.getWrapper(condition));
        
        return new PageImpl<T>(Long.valueOf(iPage.getCurrent()).intValue(),
                               Long.valueOf(iPage.getSize()).intValue(),
                               iPage.getTotal(),
                               iPage.getRecords());
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
