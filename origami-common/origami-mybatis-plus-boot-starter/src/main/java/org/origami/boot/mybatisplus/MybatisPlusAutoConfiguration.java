package org.origami.boot.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import org.origami.boot.mybatisplus.handler.MybatisPlusMetaObjectHandler;
import org.origami.boot.mybatisplus.plugins.MybatisPlusPlugins;
import org.origami.boot.mybatisplus.injector.MybatisPlusSqlInjector;
import org.origami.boot.mybatisplus.utils.SnowflakeIdGenerator;
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
        return new MybatisPlusSqlInjector();
    }

    @Bean
    @Primary
    public IdentifierGenerator snowflakeIdGenerator() {
        return new SnowflakeIdGenerator();
    }

}
