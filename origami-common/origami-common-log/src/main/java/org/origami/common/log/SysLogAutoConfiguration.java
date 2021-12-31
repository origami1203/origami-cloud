package org.origami.common.log;

import org.origami.common.log.aspect.SysLogAspect;
import org.origami.common.log.service.SysLogService;
import org.origami.common.log.service.impl.DefaultSysLogServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 系统操作日志自动配置
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2021-12-31 11:02
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication
public class SysLogAutoConfiguration {
    
    @Bean
    public SysLogAspect sysLogAspect(SysLogService sysLogService) {
        return new SysLogAspect(sysLogService);
    }
    
    @Bean
    @ConditionalOnMissingBean(SysLogService.class)
    public SysLogService sysLogService() {
        return new DefaultSysLogServiceImpl();
    }
}
