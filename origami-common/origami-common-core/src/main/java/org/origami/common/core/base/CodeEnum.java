package org.origami.common.core.base;

import lombok.Getter;
import org.origami.common.core.exception.base.BaseException;

/**
 * 状态码
 *
 * @author origami
 * @version 1.0.0
 * @date 2021-12-30 13:57
 */
public enum CodeEnum {
    
    SUCCESS("00000", "成功"),
    ERROR("-1", "请求错误"),
    USER_ERROR("A0001", "用户端错误"),
    USER_REGISTER_ERROR("A0100", "用户注册错误"),
    USER_NOT_AGREE_PRIVACY_AGREEMENT_ERROR("A0101", "用户未同意隐私协议"),
    USERNAME_VERIFY_ERROR("A0110", "用户名校验失败"),
    USERNAME_ALREADY_EXIST("A0111", "用户名已存在"),
    SENSITIVE_WORD("A0112", "用户名包含敏感词"),
    PASSWORD_VERIFY_ERROR("A0120", "密码校验失败"),
    VERIFICATION_CODE_ERROR("A0130", "验证码输入错误"),
    USER_BASIC_MESSAGE_ERROR("A0150", "用户基本信息校验失败"),
    USER_PHONE_ERROR("A0150", "手机格式校验失败"),
    USER_EMAIL_ERROR("A0153", "邮箱格式校验失败"),
    USER_LOGIN_ERROR("A0200", "用户登陆异常"),
    USER_ACCOUNT_NOT_EXISTS_ERROR("A0201", "用户账户不存在"),
    USER_NOT_LOGIN("A0202", "未登录"),
    USER_BAD_CREDENTIALS("A0203", "用户名或密码错误"),
    USER_ACCOUNT_DISABLED("A0204", "账号已被禁用"),
    NO_PERMISSION("A0300", "访问权限异常"),
    UNAUTHORIZED_ACCESS("A0301", "访问未授权"),
    USER_AUTHORIZATION_DENIED("A0303", "用户授权申请被拒绝"),
    USER_ACCESS_IS_BLOCKED("A0320", "用户访问被拦截"),
    BLACKLIST_USERS("A0321", "黑名单用户"),
    ILLEGAL_IP_ADDRESS("A0323", " 非法IP地址"),
    REQUEST_PARAM_ERROR("A0400", "用户请求参数错误"),
    RESOURCE_NOT_FOUND("A0401", "请求资源未找到"),
    USER_REQUEST_SERVICE_ERROR("A0500", "用户请求服务异常"),
    REQUEST_LIMIT_EXCEEDED("A0501", "请求次数超出限制"),
    USER_REPEATED_REQUEST("A0506", "用户重复请求"),
    UPLOAD_FILE_ERROR("A0700", "文件上传出错"),
    UPLOADED_FILE_TYPE_MISMATCH("A0702", "用户文件类型错误"),
    UPLOAD_FILE_IS_TOO_LARGE("A0702", "上传文件太大"),
    SYSTEM_ERROR("B0001", "系统执行出错"),
    SYSTEM_TIME_OUT("B0100", "系统执行超时"),
    SYSTEM_LIMITING("B0210", "系统限流"),
    SYSTEM_FALLBACK("B0220", "系统功能降级"),
    THIRD_PARTY_SERVICE_ERROR("C0001", "三方调用错误");
    
    
    @Getter
    private final String code;
    @Getter
    private final String message;
    
    
    CodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public void throwException() {
        throw new BaseException(this);
    }
}
