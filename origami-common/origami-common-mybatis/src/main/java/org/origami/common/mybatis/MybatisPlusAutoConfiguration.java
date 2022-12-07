package org.origami.common.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import org.origami.common.mybatis.config.MybatisPlusMetaObjectHandler;
import org.origami.common.mybatis.injector.ExtendSqlInjector;
import org.origami.common.mybatis.plugins.MybatisPlusPlugins;
import org.origami.common.mybatis.utils.SnowflakeIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

/**
 * mybatis-plus自动配置
 *
 * @author origami
 * @version 1.0.0
 * @date 2021-12-29 19:58
 */
@Import(MybatisPlusPlugins.class)
public class MybatisPlusAutoConfiguration {
    
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MybatisPlusMetaObjectHandler();
    }
    
    @Bean
    public ISqlInjector sqlInjector() {
        return new ExtendSqlInjector();
    }
    
    @Bean
    @Primary
    public IdentifierGenerator snowflakeIdGenerator() {
        return new SnowflakeIdGenerator();
    }
    
}
