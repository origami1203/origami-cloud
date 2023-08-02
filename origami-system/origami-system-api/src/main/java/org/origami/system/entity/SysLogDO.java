package org.origami.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.origami.boot.mybatisplus.entity.BaseEntity;

/**
 * 系统操作日志
 *
 * @author origami
 * @version 1.0.0
 * @date 2021-12-31 09:44
 */
@Data
@ApiModel(description = "系统操作日志")
@Accessors(chain = true)
@TableName(value = "sys_log")
@EqualsAndHashCode(callSuper = true)
public class SysLogDO extends BaseEntity {

    private String ip;

    private String userAgent;

    private String uri;

    @ApiModelProperty(value = "url")
    private String url;

    private String method;

    private String methodDesc;

    private Long timeConsumed;

    @ApiModelProperty(value = "请求参数")
    private String reqParams;

    @ApiModelProperty(value = "是否执行异常")
    private Boolean withExceptions;

    @ApiModelProperty(value = "发生异常时的异常信息")
    private String exceptionMsg;

    /**
     * 日志类型，登录日志0，操作日志1
     */
    private Integer type;
}
