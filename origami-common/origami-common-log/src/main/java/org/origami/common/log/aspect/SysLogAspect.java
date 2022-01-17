package org.origami.common.log.aspect;

import com.google.common.base.Strings;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.origami.common.core.utils.JacksonUtil;
import org.origami.common.core.utils.UserContextUtil;
import org.origami.common.log.base.SysLogInfo;
import org.origami.common.log.service.SysLogInfoService;
import org.origami.common.log.utils.SysLogInfoUtil;
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
    private final SysLogInfoService sysLogInfoService;
    
    @Pointcut("@annotation(org.origami.common.log.annotation.SysLog)")
    private void methodPointcut() {
    }
    
    @Pointcut("@within(org.origami.common.log.annotation.SysLog)")
    private void classPointcut() {
    }
    
    @SneakyThrows
    @Around(value = "methodPointcut() || classPointcut()")
    public Object around(ProceedingJoinPoint pjp) {
        
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        
        log.debug("[class]:{}方法被调用", method.toString());
        
        
        SysLogInfo sysLogInfo = SysLogInfoUtil.getLogInfoFromServletRequest();
        sysLogInfo.setOperatorId(UserContextUtil.getUserId())
                  .setOperatorName(UserContextUtil.getUsername())
                  .setMethodDesc(getMethodDesc(method))
                  .setMethod(method.toString())
                  .setParams(JacksonUtil.toJson(pjp.getArgs()))
                  .setWithExceptions(false);
        
        
        StopWatch stopWatch = new StopWatch();
        Object result = null;
        stopWatch.start();
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            sysLogInfo.setWithExceptions(true);
            sysLogInfo.setExceptionMsg(e.getMessage());
            // 获取异常信息后继续抛出，不要吞掉异常，抛出后到controller，全局异常处理
            throw e;
        } finally {
            stopWatch.stop();
            sysLogInfo.setTimeConsumed(stopWatch.getTotalTimeMillis())
                      .setReturnValue(JacksonUtil.toJson(result));
            // 接口，子类继承后
            sysLogInfoService.handle(sysLogInfo);
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
