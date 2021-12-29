package org.origami.common.mybatis.plugins;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 插件
 *
 * @author origami
 * @version 1.0.0
 * @date 2021-12-29 19:30
 */
@Configuration(proxyBeanMethods = false)
public class MybatisPlusPlugins {
    
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 分页插件m,使用mysql，防止每次都去查询
        PaginationInnerInterceptor paginationInnerInterceptor =
                new PaginationInnerInterceptor(DbType.MYSQL);
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor);
        // 乐观锁插件
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return mybatisPlusInterceptor;
    }
}
