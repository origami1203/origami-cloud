package org.origami.common.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 记录系统操作日志 标注于类上，类中所有方法记录操作日志 标注方法上，指定方法被记录操作日志
 *
 * @author origami1203
 * @date 2021/12/30 19:29
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Log {

    /**
     * 方法的描述， 为空则尝试寻找方法上是否有@ApiOperation注解，若有，尝试获取其value() 两者都没有,则返回被调用方法的方法名
     *
     * @return string
     */
    String value() default "";

    /**
     * 日志类型，0为登录操作日志,1为其他日志
     *
     * @return int
     */
    int type() default 1;
}
