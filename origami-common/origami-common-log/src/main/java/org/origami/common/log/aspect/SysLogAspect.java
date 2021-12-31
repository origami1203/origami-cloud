package org.origami.common.log.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 系统日志切面
 *
 * @author origami1203
 * @date 2021/12/30 19:42
 */
@Slf4j
@Aspect
public class SysLogAspect {
    
    /**
     * 被{@link org.origami.common.log.annotation.SysLog}标注的方法被记录日志
     */
    @Pointcut(value = "@annotation(org.origami.common.log.annotation.SysLog)")
    public void annotationPointcut() {
    }
    
    @Pointcut("execution(* org.origami..*.controller.*(..))")
    public void controllerPointcut() {
    }
    
    @Around(value = "annotationPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return null;
    }
    
    private
}
