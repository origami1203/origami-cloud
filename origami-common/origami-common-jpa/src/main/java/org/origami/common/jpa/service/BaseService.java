package org.origami.common.jpa.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.origami.common.core.data.page.IPage;
import org.origami.common.core.data.query.PageModel;
import org.origami.common.core.data.query.QueryModel;
import org.origami.common.core.exception.NotFoundException;
import org.origami.common.core.utils.Assert;
import org.origami.common.jpa.entity.BaseEntity;
import org.origami.common.jpa.repository.BaseRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * service基类，参照mybatis-plus的IService
 *
 * @author origami
 * @date 2022/1/1 21:06
 */
public interface BaseService<T extends BaseEntity> {

    /**
     * 获取对应 entity 的 BaseMapper
     *
     * @return BaseMapper
     */
    BaseRepository<T> getBaseRepository();

    /**
     * 页面
     *
     * @param condition 条件
     * @return {@code IPage<T>}
     */
    default IPage<T> page(PageModel<T> condition) {
        return getBaseRepository().page(condition);
    }


    default T save(T entity) {
        return getBaseRepository().save(entity);
    }

    /**
     * 保存批 插入（批量）
     *
     * @param entityList 实体对象集合
     * @return {@code Iterable<T>}
     */
    @Transactional(rollbackFor = Exception.class)
    default Iterable<T> saveBatch(Collection<T> entityList) {
        return getBaseRepository().saveAll(entityList);
    }


    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    default boolean removeById(Long id) {
        getBaseRepository().deleteById(id);
        return true;
    }

    /**
     * 根据实体(ID)删除
     *
     * @param entity 实体
     * @since 3.4.4
     */
    default boolean remove(T entity) {
        getBaseRepository().deleteById(entity.getId());
        return true;
    }


    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表
     */
    @Transactional(rollbackFor = Exception.class)
    default void removeByIds(Iterable<Long> idList) {
        getBaseRepository().deleteAllById(idList);
    }

    /**
     * 根据 ID 选择修改
     *
     * @param entity 实体对象
     */
    default T update(T entity) {
        Assert.nonNull(entity, "更新的实体类不能为null");
        Assert.nonNull(entity.getId(), "更新的实体类id不能为null");

        T dbEntity = Optional.ofNullable(getById(entity.getId()))
                .orElseThrow(() -> new NotFoundException("更新对象不存在"));
        entity.setVersion(dbEntity.getVersion());

        return getBaseRepository().save(entity);
    }


    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     */
    @Transactional(rollbackFor = Exception.class)
    default Iterable<T> updateBatch(Iterable<T> entityList) {
        return getBaseRepository().saveAll(entityList);
    }


    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    default T getById(Long id) {
        return getBaseRepository().getById(id);
    }

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表
     */
    default List<T> findAllByIds(Iterable<Long> idList) {
        return getBaseRepository().findAllById(idList);
    }


    /**
     * 查询总记录数
     */
    default long count() {
        return getBaseRepository().count();
    }

    /**
     * 根据 Wrapper 条件，查询总记录数
     */
    default long count(QueryModel<T> queryModel) {
        return getBaseRepository().count(queryModel);
    }

    /**
     * 查询列表
     */
    default List<T> list(QueryModel<T> queryModel) {
        return getBaseRepository().list(queryModel);
    }

    /**
     * 查询所有
     */
    default List<T> list() {
        return getBaseRepository().findAll();
    }


}
