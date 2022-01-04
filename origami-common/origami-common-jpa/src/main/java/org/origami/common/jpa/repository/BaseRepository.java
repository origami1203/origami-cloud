package org.origami.common.jpa.repository;

import org.origami.common.core.base.BaseEntity;
import org.origami.common.jpa.condition.impl.PageQueryCondition;
import org.origami.common.jpa.condition.impl.QueryCondition;
import org.origami.common.jpa.utils.SpecificationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * repository基类
 *
 * @author origami
 * @date 2022/1/1 21:05
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends
                                                      PagingAndSortingRepository<T, Long>,
                                                      JpaSpecificationExecutor<T> {
    
    /**
     * 条件分页查询
     *
     * @param pageQuery
     * @return
     */
    default Page<T> page(PageQueryCondition<?> pageQuery) {
        
        Specification<T> spec = SpecificationUtil.getSpecification(pageQuery);
        
        Sort sort = pageQuery.getSort(pageQuery.getOrders());
        Pageable page = PageRequest.of(pageQuery.getPageNum(), pageQuery.getPageSize(), sort);
        
        return findAll(spec, page);
    }
    
    /**
     * 条件查询list
     *
     * @param condition
     * @return
     */
    default List<T> list(QueryCondition<?> condition) {
        Specification<T> spec = SpecificationUtil.getSpecification(condition);
        return findAll(spec, condition.getSort(condition.getOrders()));
    }
    
    default Long count(QueryCondition<?> condition) {
        Specification<T> spec = SpecificationUtil.getSpecification(condition);
        return count(spec);
    }
    
    
    @Override
    @Query(value = "select e from #{#entityName} e where e.deleted = false")
    List<T> findAll();
    
    @Override
    @Query(value = "select e from #{#entityName} e where e.deleted = false order by ?1 ")
    List<T> findAll(Sort sort);
    
    @Override
    @Query(value = "select e from #{#entityName} e where e.deleted= false and e.id in ?1")
    List<T> findAllById(Iterable<Long> longs);
    
    @Override
    default List<T> findAll(Specification<T> spec) {
        return findAll(spec, Sort.unsorted());
    }
    
    @Override
    default Optional<T> findById(Long aLong) {
        return Optional.ofNullable(getById(aLong));
    }
    
    @Query(value = "select e from #{#entityName} e where e.deleted = false and e.id = ?1")
    T getById(Long id);
    
    @Override
    default boolean existsById(Long aLong) {
        return getById(aLong) != null;
    }
    
    @Override
    @Query(value = "select count(e) from #{#entityName} e where e.deleted = false")
    long count();
    
    @Override
    @Modifying
    @Query(value = "update #{#entityName} e set e.deleted = true where e.id = ?1")
    void deleteById(Long aLong);
    
    @Override
    default void delete(T entity) {
        deleteById(entity.getId());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    default void deleteAllById(Iterable<? extends Long> longs) {
        longs.forEach(this::deleteById);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    default void deleteAll(Iterable<? extends T> entities) {
        entities.forEach(entity -> deleteById(entity.getId()));
    }
    
    @Override
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update #{#entityName} e set e.deleted = true")
    void deleteAll();
    
}
