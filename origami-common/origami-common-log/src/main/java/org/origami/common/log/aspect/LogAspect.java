package org.origami.common.log.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.origami.common.core.utils.CurrentUserHolder;
import org.origami.common.core.utils.JsonUtil;
import org.origami.common.log.annotation.Log;
import org.origami.common.log.base.OperationLogInfo;
import org.origami.common.log.service.LogInfoService;
import org.origami.common.log.utils.LogInfoHelper;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StopWatch;

import com.google.common.base.Strings;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统日志切面
 *
 * @author origami1203
 * @date 2021/12/30 19:42
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class LogAspect {

    private final LogInfoService logInfoService;

    /**
     * 切面方法上的@Log注解
     */
    @Pointcut("@annotation(org.origami.common.log.annotation.Log)")
    private void logMethod() {}

    /**
     * 类上的@Log注解
     */
    @Pointcut("@within(org.origami.common.log.annotation.Log)")
    private void logClass() {}

    @SneakyThrows
    @Around(value = "logMethod() || logClass()")
    public Object around(ProceedingJoinPoint pjp) {

        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        Method method = methodSignature.getMethod();

        log.debug("[class]:{}方法被调用", method.toString());

        OperationLogInfo operationLogInfo = LogInfoHelper.getLogInfoFromServletRequest();
        operationLogInfo.setOperatorId(CurrentUserHolder.getUserId(Long.class))
            .setOperatorName(CurrentUserHolder.getUsername()).setMethodDesc(getMethodDesc(method))
            .setMethod(method.toString()).setReqParams(JsonUtil.toJson(pjp.getArgs())).setWithExceptions(false)
            .setType(getType(method));

        StopWatch stopWatch = new StopWatch();
        Object result = null;
        stopWatch.start();
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            operationLogInfo.setWithExceptions(true);
            operationLogInfo.setExceptionMsg(e.getMessage());
            // 获取异常信息后继续抛出，不要吞掉异常，抛出后到controller，全局异常处理
            throw e;
        } finally {
            stopWatch.stop();
            operationLogInfo.setTimeConsumed(stopWatch.getTotalTimeMillis());
            if (result == null) {
                operationLogInfo.setReturnValue("null");
            } else {
                operationLogInfo.setReturnValue(JsonUtil.toJson(result));
            }
            // 接口，子类继承后
            logInfoService.handle(operationLogInfo);
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

        Log annotation = AnnotationUtils.getAnnotation(method, Log.class);

        if (annotation != null) {
            if (!Strings.isNullOrEmpty(annotation.value())) {
                desc = annotation.value();
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

    private int getType(Method method) {
        Log annotation = AnnotationUtils.getAnnotation(method, Log.class);
        if (annotation == null) {
            annotation = AnnotationUtils.getAnnotation(method.getDeclaringClass(), Log.class);
        }
        return annotation.type();
    }

}
