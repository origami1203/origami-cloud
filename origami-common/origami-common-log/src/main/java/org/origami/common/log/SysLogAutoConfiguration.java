package org.origami.common.log;

import lombok.extern.slf4j.Slf4j;
import org.origami.common.log.aspect.SysLogAspect;
import org.origami.common.log.service.SysLogInfoService;
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
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication
public class SysLogAutoConfiguration {
    
    @Bean
    public SysLogAspect sysLogAspect(SysLogInfoService sysLogInfoService) {
        return new SysLogAspect(sysLogInfoService);
    }
    
    @Bean
    @ConditionalOnMissingBean(SysLogInfoService.class)
    public SysLogInfoService sysLogService() {
        return sysLogInfo -> log.debug("收到了一个系统操作日志：{}，然后把它丢弃了", sysLogInfo);
    }
}
