package org.origami.webmvc;

import org.origami.webmvc.config.WebMvcConfig;
import org.origami.webmvc.handler.GlobalExceptionHandler;
import org.origami.webmvc.interceptor.FromInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author origami
 * @date 2022/1/27 20:23
 */
@Import({WebMvcConfig.class, GlobalExceptionHandler.class})
public class WebMvcAutoConfiguration {
}
