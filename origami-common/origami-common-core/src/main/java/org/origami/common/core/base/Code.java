package org.origami.common.core.base;

import lombok.Getter;

/**
 * 状态码
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2021-12-30 13:57
 */
public enum Code {

    SUCCESS("00000", "成功"),
    ERROR("-1", "请求错误"),
    USER_ERROR("A0000", "用户端错误"),
    USER_NOT_EXIST("A0201", "账户不存在"),
    USER_NOT_LOGIN("A0202", "未登录"),
    NO_PERMISSION("A0300", "没有权限"),
    REQUEST_PARAM_ERROR("A0400", "用户请求参数错误"),
    RESOURCE_NOT_FOUND("A0401", "请求资源未找到"),
    SYSTEM_ERROR("B0001","系统执行出错"),
    SYSTEM_TIME_OUT("B0100","系统执行超时"),
    SYSTEM_LIMITING("B0210","系统限流"),
    SYSTEM_FALLBACK("B0220", "系统功能降级");


    @Getter
    private final String code;
    @Getter
    private final String message;


    Code(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
