package org.origami.common.jpa.service;

import org.origami.common.core.base.BaseEntity;
import org.origami.common.jpa.condition.impl.PageQueryCondition;
import org.origami.common.jpa.condition.impl.QueryCondition;
import org.origami.common.jpa.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * service基类，参照mybatis-plus的IService
 *
 * @author origami
 * @date 2022/1/1 21:06
 */
@SuppressWarnings("unchecked")
public interface BaseService<T extends BaseEntity> {
    
    /**
     * 获取对应 entity 的 BaseMapper
     *
     * @return BaseMapper
     */
    BaseRepository<T> getBaseRepository();
    
    default Page<T> page(PageQueryCondition<?> condition) {
        return getBaseRepository().page(condition);
    }
    
    
    default T save(T entity) {
        return getBaseRepository().save(entity);
    }
    
    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
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
    default void removeById(Long id) {
        getBaseRepository().deleteById(id);
    }
    
    /**
     * 根据实体(ID)删除
     *
     * @param entity 实体
     * @since 3.4.4
     */
    default void removeById(T entity) {
        getBaseRepository().deleteById(entity.getId());
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
    default T updateById(T entity) {
        return getBaseRepository().save(entity);
    }
    
    
    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     */
    @Transactional(rollbackFor = Exception.class)
    default Iterable<T> updateBatchById(Iterable<T> entityList) {
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
    default List<T> findAllById(Iterable<Long> idList) {
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
    default long count(QueryCondition<T> condition) {
        return getBaseRepository().count(condition);
    }
    
    /**
     * 查询列表
     */
    default List<T> list(QueryCondition<T> condition) {
        return getBaseRepository().list(condition);
    }
    
    /**
     * 查询所有
     */
    default List<T> list() {
        return getBaseRepository().findAll();
    }
    
    
}
