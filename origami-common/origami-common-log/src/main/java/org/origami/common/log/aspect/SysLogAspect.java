package org.origami.common.log.aspect;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.origami.common.log.utils.SysLogUtil;
import org.origami.upm.api.entity.SysLog;
import org.origami.upm.api.service.SysLogService;
import org.springframework.util.StopWatch;

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


    @Pointcut("execution(* org.origami..*.controller.*(..))")
    public void controllerPointcut() {
    }

    // TODO 若@SysLog的方法描述未填写,尝试从@ApiOperation中获取
    @Around(value = "@annotation(sysLogEnum)")
    public Object around(ProceedingJoinPoint joinPoint, org.origami.common.log.annotation.SysLog sysLogEnum) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        log.debug("[类名]:{},[方法]:{}被调用", className, methodName);

        SysLog sysLog = SysLogUtil.getLogFromRequest();
        sysLog.setMethod(className + "#" + methodName);
        sysLog.setMethodDesc(sysLogEnum.value());
        sysLog.setParams(JSON.toJSONString(joinPoint.getArgs()));
        sysLog.setWithExceptions(false);

        StopWatch stopWatch = new StopWatch();
        Object result = null;
        stopWatch.start();
        try {
            result = joinPoint.proceed();
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

}
