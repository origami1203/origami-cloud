package org.origami.common.core.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 返回结果模型
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2021-12-30 13:51
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 3436193278972216239L;
    
    /**
     * 状态码
     */
    private String code;
    /**
     * 成功或错误信息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;
    
    public static Result<Void> ok() {
        return ok(null);
    }
    
    public static <T> Result<T> ok(T data) {
        return ok(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage(), data);
    }
    
    public static <T> Result<T> ok(String code, String msg, T data) {
        return new Result<T>().setCode(code).setMsg(msg).setData(data);
    }
    
    public static Result<Void> failed(String msg) {
        return new Result<>(Code.ERROR.getCode(), msg, null);
    }
    
    public static Result<Void> failed(String code, String msg) {
        return new Result<>(code, msg, null);
    }
    
}
