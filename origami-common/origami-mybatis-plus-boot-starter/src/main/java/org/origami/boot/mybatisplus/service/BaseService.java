package org.origami.boot.mybatisplus.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import java.util.Collection;
import java.util.List;
import org.origami.boot.mybatisplus.entity.BaseEntity;
import org.origami.boot.mybatisplus.mapper.BaseMapper;
import org.origami.boot.mybatisplus.utils.WrapperUtil;
import org.origami.common.core.data.page.IPage;
import org.origami.common.core.data.page.impl.PageImpl;
import org.origami.common.core.data.query.PageModel;
import org.origami.common.core.data.query.QueryModel;
import org.origami.common.core.utils.Assert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 自定义基类BaseService,不继承mp的IService</br>
 * 因为调用mp的IService内的方法实现不走乐观锁，因为没有先查询再更新
 *
 * @author origami
 * @date 2022/1/3 17:00
 */
public interface BaseService<T extends BaseEntity> {

    /**
     * 获取baseMapper
     *
     * @return {@code BaseMapper}
     */
    BaseMapper<T> getBaseMapper();

    /**
     * 新增实体类
     *
     * @param entity 实体
     * @return boolean
     */
    default boolean save(T entity) {
        return Db.save(entity);
    }

    /**
     * 更新实体类，走乐观锁
     *
     * @param entity 实体
     * @return 是否更新成功
     */
    boolean updateById(T entity);

    /**
     * 通过id删除，当已被删除的数据再次删除时，直接返回true
     *
     * @param id id
     * @return 是否删除成功
     */
    default boolean removeById(Long id) {
        getBaseMapper().deleteById(id);
        return true;
    }

    /**
     * 通过id获取
     *
     * @param id id
     * @return {@code T}
     */
    default T getById(Long id) {
        Assert.nonNull(id, "id不能为null");
        return getBaseMapper().selectById(id);
    }

    /**
     * 返回所有数据
     *
     * @return {@code List<T>}
     */
    default List<T> list() {
        return getBaseMapper().selectList(Wrappers.emptyWrapper());
    }

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
        com.baomidou.mybatisplus.core.metadata.IPage<T> page =
            getBaseMapper().selectPage(pageDTO, WrapperUtil.getWrapper(pageModel.toPageQuery()));

        return PageImpl.of(Long.valueOf(page.getCurrent()).intValue(), Long.valueOf(page.getSize()).intValue(),
            page.getTotal(), page.getRecords());
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

    /**
     * 通过id列表
     *
     * @param ids id
     * @return {@code List<T>}
     */
    default List<T> listByIds(Collection<Long> ids) {
        return getBaseMapper().selectBatchIds(ids);
    }

    /**
     * 批量插入
     *
     * @param entities 实体
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean saveBatch(Collection<T> entities) {
        return Db.saveBatch(entities);
    }

}
