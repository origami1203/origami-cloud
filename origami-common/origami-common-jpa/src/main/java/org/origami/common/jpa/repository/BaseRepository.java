package org.origami.common.jpa.repository;

import org.origami.common.core.data.page.IPage;
import org.origami.common.core.data.page.impl.PageImpl;
import org.origami.common.core.data.query.PageModel;
import org.origami.common.core.data.query.PageQuery;
import org.origami.common.core.data.query.Query;
import org.origami.common.core.data.query.QueryModel;
import org.origami.common.core.data.query.impl.PageQueryImpl;
import org.origami.common.core.data.query.impl.QueryImpl;
import org.origami.common.jpa.entity.BaseEntity;
import org.origami.common.jpa.utils.SpecificationUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
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
public interface BaseRepository<T extends BaseEntity> extends CrudRepository<T, Long>,
        JpaSpecificationExecutor<T> {

    /**
     * 分页查询
     *
     * @param pageModel 页面查询
     * @return {@code IPage<T>}
     */
    default IPage<T> page(PageModel<T> pageModel) {

        Specification<T> spec = SpecificationUtil.getSpecification(pageModel.toPageQuery());

        PageRequest pageRequest = PageRequest.of(pageModel.getPageNum(),
                pageModel.getPageSize(),
                SpecificationUtil.getJpaSort(pageModel.getSort()));

        org.springframework.data.domain.Page<T> pages = findAll(spec, pageRequest);

        return PageImpl.of(pages.getNumber(),
                pages.getSize(),
                pages.getTotalElements(),
                pages.getContent());
    }

    /**
     * 条件查询list
     *
     * @param queryModel 条件
     * @return {@code List<T>}
     */
    default List<T> list(QueryModel<T> queryModel) {

        Specification<T> spec = SpecificationUtil.getSpecification(queryModel.toQuery());

        return findAll(spec, SpecificationUtil.getJpaSort(queryModel.getSort()));
    }

    default Long count(QueryModel<T> queryModel) {
        Specification<T> spec = SpecificationUtil.getSpecification(queryModel.toQuery());
        return count(spec);
    }


    @Override
    @org.springframework.data.jpa.repository.Query(value = "select e from #{#entityName} e where e.deleted = false")
    List<T> findAll();


    @Override
    @org.springframework.data.jpa.repository.Query(value = "select e from #{#entityName} e " +
            "where e.id in ?1 and e.deleted= false")
    List<T> findAllById(Iterable<Long> longs);

    @Override
    default List<T> findAll(Specification<T> spec) {
        return findAll(spec, Sort.unsorted());
    }

    @Override
    default Optional<T> findById(Long id) {
        return Optional.ofNullable(getById(id));
    }

    @org.springframework.data.jpa.repository.Query(value = "select e from #{#entityName} e " +
            "where e.id = ?1 and e.deleted = false")
    T getById(Long id);

    @Override
    default boolean existsById(Long aLong) {
        return getById(aLong) != null;
    }

    @Override
    @org.springframework.data.jpa.repository.Query(value = "select count(e) from #{#entityName} e where e.deleted = false")
    long count();

    @Override
    @Modifying
    @org.springframework.data.jpa.repository.Query(value =
            "update #{#entityName} e set e.deleted = true " +
                    "where e.id = ?1")
    void deleteById(Long id);

    @Override
    default void delete(T entity) {
        deleteById(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    default void deleteAllById(Iterable<? extends Long> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    default void deleteAll(Iterable<? extends T> entities) {
        entities.forEach(entity -> deleteById(entity.getId()));
    }

    @Override
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @org.springframework.data.jpa.repository.Query(value = "update #{#entityName} e set e.deleted = true")
    void deleteAll();

}
