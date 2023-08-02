package org.origami.webmvc.handler;

import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.origami.common.core.base.CodeEnum;
import org.origami.common.core.base.R;
import org.origami.common.core.exception.base.BaseException;
import org.origami.webmvc.filter.ExceptionHandlingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * Controller的全局异常处理</br>
 * 过滤器异常处理
 *
 * @author origami
 * @date 2022/1/17 23:15
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Resource
    private HandlerExceptionResolver handlerExceptionResolver;

    /**
     * 处理程序的未知异常
     *
     * @param e e
     * @return {@code R<Void>}
     */
    @ExceptionHandler(Exception.class)
    public R<Void> exception(Exception e) {
        printExceptionToConsole(e);
        return R.failed(CodeEnum.SYSTEM_ERROR);
    }

    /**
     * 用户输入的json错误
     *
     * @param e e
     * @return {@code R<Void>}
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R<Void> jsonException(HttpMessageNotReadableException e) {
        printSimpleExceptionMessage(e);
        return R.failed(CodeEnum.REQUEST_PARAM_ERROR.getCode(), "json错误");
    }

    /**
     * bean验证相关的异常
     *
     * @param e e
     * @return {@code R<Void>}
     */
    @ExceptionHandler({ConstraintViolationException.class, ServletRequestBindingException.class, BindException.class})
    public R<Void> validationException(Exception e) {
        R<Void> result = R.failed(CodeEnum.REQUEST_PARAM_ERROR);

        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException)e;

            return result.setMsg(ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(";")));
        }

        if (e instanceof BindException) {
            BindException ex = (BindException)e;

            return result.setMsg(ex.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(";")));
        }
        return result.setMsg(e.getMessage());
    }

    /**
     * 业务异常
     *
     * @param e e
     * @return {@code R<Void>}
     */
    @ExceptionHandler(BaseException.class)
    public R<Void> baseException(BaseException e) {
        printSimpleExceptionMessage(e);
        return R.failed(e.getCode(), e.getMessage());
    }

    private void printExceptionToConsole(Exception e) {
        log.error("堆栈信息", e);
    }

    private void printSimpleExceptionMessage(Exception e) {
        log.error(e.getMessage());
    }

    @Bean
    public FilterRegistrationBean<ExceptionHandlingFilter> exceptionHandlingFilter() {
        FilterRegistrationBean<ExceptionHandlingFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new ExceptionHandlingFilter(handlerExceptionResolver));
        filter.setOrder(-100);
        return filter;
    }
}
