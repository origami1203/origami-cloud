package org.origami.common.log;

import lombok.extern.slf4j.Slf4j;
import org.origami.common.log.aspect.LogAspect;
import org.origami.common.log.service.LogInfoService;
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
public class OperationLogAutoConfiguration {

    @Bean
    public LogAspect logAspect(LogInfoService logInfoService) {
        return new LogAspect(logInfoService);
    }
}
