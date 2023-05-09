package org.origami.common.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.origami.common.jpa.config.UserAuditorAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * jpa自动配置类
 *
 * @author origami
 * @date 2022/1/2 9:50
 */
@EnableJpaAuditing
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class SpringDataJpaAutoConfiguration {
    
    @PersistenceContext
    private final EntityManager entityManager;
    
    @Bean
    public UserAuditorAware userAuditorAware() {
        return new UserAuditorAware();
    }
    
    @Bean
    public JPAQueryFactory jpaQuery() {
        return new JPAQueryFactory(entityManager);
    }
}
