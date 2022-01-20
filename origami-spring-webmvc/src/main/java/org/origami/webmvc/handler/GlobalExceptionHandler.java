package org.origami.webmvc.handler;

import lombok.extern.slf4j.Slf4j;
import org.origami.common.core.base.Result;
import org.origami.common.core.exception.base.BaseException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * @author origami
 * @date 2022/1/17 23:15
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public Result<Void> exception(Exception e) {
        return Result.failed(e.getMessage());
    }
    
    @ExceptionHandler({ConstraintViolationException.class,
                       ServletRequestBindingException.class,
                       BindException.class})
    public Result<Void> validationException(Exception e) {
        Result<Void> result = new Result<>();
        
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException ex =
                    (ConstraintViolationException) e;
            
            return result.setMsg(ex.getConstraintViolations()
                                   .stream()
                                   .map(ConstraintViolation::getMessage)
                                   .collect(Collectors.joining(";")));
        }
        
        if (e instanceof BindException) {
            BindException ex =
                    (BindException) e;
            
            return result.setMsg(ex.getAllErrors()
                                   .stream()
                                   .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                   .collect(Collectors.joining(";")));
        }
    
        // if (e instanceof ServletRequestBindingException) {
        //     ServletRequestBindingException ex =
        //             (ServletRequestBindingException) e;
        //
        // }
    
        return result;
    }
    
    @ExceptionHandler(BaseException.class)
    public Result<Void> baseException(BaseException e) {
        return Result.failed(e.getCode(), e.getMessage());
    }
}
