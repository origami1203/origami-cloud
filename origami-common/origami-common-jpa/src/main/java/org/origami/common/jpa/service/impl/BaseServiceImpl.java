package org.origami.common.jpa.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.origami.common.jpa.entity.BaseEntity;
import org.origami.common.jpa.repository.BaseRepository;
import org.origami.common.jpa.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @author origami
 * @date 2022/1/2 19:20
 */
@Slf4j
public class BaseServiceImpl<R extends BaseRepository<T>, T extends BaseEntity> implements
                                                                            BaseService<T> {
    @Resource
    protected R baseRepository;
    
    @Resource
    protected JPAQueryFactory jpaQuery;
    
    
    
    @Override
    public BaseRepository<T> getBaseRepository() {
        return baseRepository;
    }
    
}
