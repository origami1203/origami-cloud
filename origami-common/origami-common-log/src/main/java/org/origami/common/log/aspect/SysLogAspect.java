package org.origami.common.log.aspect;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.origami.common.log.utils.SysLogUtil;
import org.origami.upm.api.entity.SysLog;
import org.origami.upm.api.service.SysLogService;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

/**
 * 系统日志切面
 *
 * @author origami1203
 * @date 2021/12/30 19:42
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class SysLogAspect {
    
    private final SysLogService sysLogService;
    
    @Pointcut("@annotation(org.origami.common.log.annotation.SysLog)")
    private void methodPointcut() {
    }
    
    @Pointcut("@within(org.origami.common.log.annotation.SysLog)")
    private void classPointcut() {
    }
    
    @Around(value = "methodPointcut() || classPointcut()")
    public Object around(ProceedingJoinPoint pjp) {
        
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        
        log.debug("[class]:{}方法被调用", method.toString());
        
        
        SysLog sysLog = SysLogUtil.getLogFromRequest();
        sysLog.setMethodDesc(getMethodDesc(method));
        sysLog.setMethod(method.toString());
        sysLog.setParams(JSON.toJSONString(pjp.getArgs()));
        sysLog.setWithExceptions(false);
        
        
        StopWatch stopWatch = new StopWatch();
        Object result = null;
        stopWatch.start();
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            sysLog.setWithExceptions(true);
            sysLog.setExceptionMsg(e.getMessage());
        } finally {
            stopWatch.stop();
            sysLog.setTime(stopWatch.getTotalTimeMillis());
            sysLogService.save(sysLog);
        }
        
        return result;
    }
    
    /**
     * 获取被执行方法的方法描述
     *
     * @param method 被调用的方法
     * @return 描述
     */
    private String getMethodDesc(Method method) {
        String desc = method.getName();
        
        if (method.isAnnotationPresent(org.origami.common.log.annotation.SysLog.class)) {
            org.origami.common.log.annotation.SysLog sysLogAnnotation =
                    method.getAnnotation(org.origami.common.log.annotation.SysLog.class);
            
            
            if (!Strings.isNullOrEmpty(sysLogAnnotation.value())) {
                desc = sysLogAnnotation.value();
                return desc;
            }
        }
        if (!Strings.isNullOrEmpty(getApiOperationValue(method))) {
            desc = getApiOperationValue(method);
            return desc;
        }
        return desc;
    }
    
    private String getApiOperationValue(Method method) {
        String desc = null;
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation annotation = method.getAnnotation(ApiOperation.class);
            desc = annotation.value();
        }
        return desc;
    }
    
    
}
