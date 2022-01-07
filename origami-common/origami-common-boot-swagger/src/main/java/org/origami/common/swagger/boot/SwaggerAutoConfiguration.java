package org.origami.common.swagger.boot;

import org.origami.common.swagger.boot.config.SwaggerConfig;
import org.springframework.context.annotation.Import;

/**
 * 自动配置
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-01-07 10:18
 */
@Import(SwaggerConfig.class)
public class SwaggerAutoConfiguration {
}
