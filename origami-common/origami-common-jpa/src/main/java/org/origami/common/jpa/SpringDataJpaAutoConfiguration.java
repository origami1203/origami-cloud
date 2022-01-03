package org.origami.common.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.origami.common.jpa.config.UserAuditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 自动配置类
 *
 * @author origami
 * @date 2022/1/2 9:50
 */
@EnableJpaAuditing
@Configuration(proxyBeanMethods = false)
public class SpringDataJpaAutoConfiguration {
    @Autowired
    @PersistenceContext
    private EntityManager entityManager;
    
    @Bean
    public UserAuditor userAuditor() {
        return new UserAuditor();
    }
    
    @Bean
    public JPAQueryFactory jpaQuery() {
        return new JPAQueryFactory(entityManager);
    }
}
