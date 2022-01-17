package org.origami.webmvc;

import org.origami.webmvc.config.WebMvcConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * mvc通用配置,jackson和跨域
 *
 * @author origami
 * @date 2022/1/15 17:54
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(WebMvcConfig.class)
public @interface EnableWebMvcConfig {
}
