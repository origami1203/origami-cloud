package org.origami.common.mybatis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import org.origami.common.core.data.page.IPage;
import org.origami.common.core.data.page.impl.PageImpl;
import org.origami.common.core.data.query.PageModel;
import org.origami.common.core.data.query.QueryModel;
import org.origami.common.mybatis.entity.BaseEntity;
import org.origami.common.mybatis.utils.WrapperUtil;

/**
 * 自定义基类BaseService拓展mp的IService
 *
 * @author origami
 * @date 2022/1/3 17:00
 */
public interface BaseService<T extends BaseEntity> extends IService<T> {

    /**
     * 根据条件查询列表
     *
     * @param queryModel 条件
     * @return {@code List<T>}
     */
    default List<T> list(QueryModel<T> queryModel) {
        return getBaseMapper().selectList(WrapperUtil.getWrapper(queryModel.toQuery()));
    }

    /**
     * 条件查询分页
     *
     * @param pageModel 条件
     * @return {@code IPage<T>}
     */
    default IPage<T> page(PageModel<T> pageModel) {
        PageDTO<T> pageDTO = new PageDTO<>(pageModel.getPageNum(), pageModel.getPageSize());
        com.baomidou.mybatisplus.core.metadata.IPage<T>
                page = getBaseMapper().selectPage(pageDTO,
                WrapperUtil.getWrapper(pageModel.toPageQuery()));

        return PageImpl.of(Long.valueOf(page.getCurrent()).intValue(),
                Long.valueOf(page.getSize()).intValue(),
                page.getTotal(),
                page.getRecords());
    }

    /**
     * 条件查询计数
     *
     * @param queryModel 条件
     * @return long
     */
    default long count(QueryModel<T> queryModel) {
        return getBaseMapper().selectCount(WrapperUtil.getWrapper(queryModel.toQuery()));
    }

}
