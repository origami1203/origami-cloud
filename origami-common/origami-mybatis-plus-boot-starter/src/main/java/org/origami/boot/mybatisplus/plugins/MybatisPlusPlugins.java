package org.origami.boot.mybatisplus.plugins;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.origami.common.core.exception.IllegalOperationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 插件
 *
 * @author origami
 * @version 1.0.0
 * @date 2021-12-29 19:30
 */
@Configuration
public class MybatisPlusPlugins {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 分页插件
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        // 乐观锁插件
        OptimisticLockerInnerInterceptor optimisticLocker = new OptimisticLockerInnerInterceptor();
        optimisticLocker.setException(new IllegalOperationException("乐观锁锁冲突"));
        mybatisPlusInterceptor.addInnerInterceptor(optimisticLocker);
        return mybatisPlusInterceptor;
    }
}
