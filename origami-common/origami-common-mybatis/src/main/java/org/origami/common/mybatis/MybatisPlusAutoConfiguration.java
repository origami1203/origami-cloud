package org.origami.common.mybatis;

import org.origami.common.mybatis.config.MybatisPlusMetaObjectHandler;
import org.origami.common.mybatis.plugins.MybatisPlusPlugins;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * mybatis-plus自动配置
 *
 * @author origami
 * @version 1.0.0
 * @date 2021-12-29 19:58
 */
@Import(MybatisPlusPlugins.class)
@Configuration
public class MybatisPlusAutoConfiguration {
    
    @Bean
    public MybatisPlusMetaObjectHandler metaObjectHandler() {
        return new MybatisPlusMetaObjectHandler();
    }
    
}
