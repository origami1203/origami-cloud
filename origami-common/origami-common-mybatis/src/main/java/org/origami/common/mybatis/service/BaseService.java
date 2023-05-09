package org.origami.common.mybatis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.origami.common.core.data.page.IPage;
import org.origami.common.core.data.page.impl.PageImpl;
import org.origami.common.core.data.query.PageQuery;
import org.origami.common.core.data.query.Query;
import org.origami.common.mybatis.entity.BaseEntity;
import org.origami.common.mybatis.utils.WrapperUtil;

import java.util.List;
import org.springframework.cglib.beans.BeanCopier;

/**
 * 自定义基类BaseService
 *
 * @author origami
 * @date 2022/1/3 17:00
 */
public interface BaseService<T extends BaseEntity> extends IService<T> {

    /**
     * 条件查询
     *
     * @param condition 条件
     * @return {@code List<T>}
     */
    default List<T> list(Query<T> condition) {
        return getBaseMapper().selectList(WrapperUtil.getWrapper(condition));
    }

    /**
     * 条件查询分页
     *
     * @param condition 条件
     * @return {@code IPage<T>}
     */
    default IPage<T> page(PageQuery<T> condition) {
        PageDTO<T> pageDTO = new PageDTO<>(condition.getPageNum(), condition.getPageSize());
        com.baomidou.mybatisplus.core.metadata.IPage<T>
                page = getBaseMapper().selectPage(pageDTO,
                WrapperUtil.getWrapper(condition));

        return PageImpl.of(Long.valueOf(page.getCurrent()).intValue(),
                Long.valueOf(page.getSize()).intValue(),
                page.getTotal(),
                page.getRecords());
    }

    /**
     * 条件查询计数
     *
     * @param condition 条件
     * @return long
     */
    default long count(Query<T> condition) {
        return getBaseMapper().selectCount(WrapperUtil.getWrapper(condition));
    }

}
