package org.origami.upm.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.origami.common.mybatis.entity.BaseEntity;

/**
 * 系统操作日志
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2021-12-31 09:44
 */
@Data
@ApiModel(description = "系统操作日志")
@Accessors(chain = true)
@TableName(value = "sys_log")
@EqualsAndHashCode(callSuper = true)
public class SysLog extends BaseEntity {

    @ApiModelProperty(value = "被调用方案的描述")
    private String methodDesc;

    @ApiModelProperty(value = "用户ip")
    private String ip;

    @ApiModelProperty(value = "浏览器代理")
    private String userAgent;

    @ApiModelProperty(value = "uri")
    private String uri;

    @ApiModelProperty(value = "url")
    private String url;

    @ApiModelProperty(value = "调用的controller方法")
    private String method;

    @ApiModelProperty(value = "方法耗时")
    private Long time;

    @ApiModelProperty(value = "请求参数")
    private String params;

    @ApiModelProperty(value = "是否执行异常")
    private Boolean withExceptions;

    @ApiModelProperty(value = "发生异常时的异常信息")
    private String exceptionMsg;
}
