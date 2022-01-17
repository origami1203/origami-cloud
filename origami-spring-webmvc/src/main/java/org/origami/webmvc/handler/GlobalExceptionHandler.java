package org.origami.webmvc.handler;

import org.origami.common.core.base.Result;
import org.origami.common.core.exception.IllegalParamException;
import org.origami.common.core.exception.base.BaseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author origami
 * @date 2022/1/17 23:15
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BaseException.class)
    public Result<Void> baseException(BaseException e) {
        return new Result<Void>().setCode(e.getCode()).setMsg(e.getMessage());
    }
    
    @ExceptionHandler(IllegalParamException.class)
    public Result<Void> illegalParamException(BaseException e) {
        return new Result<Void>().setCode(e.getCode()).setMsg(e.getMessage());
    }
    
}
