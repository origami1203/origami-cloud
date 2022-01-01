package org.origami.common.log.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统操作日志类
 *
 * @author origami
 * @date 2022/1/1 7:09
 */
@Data
@Accessors(chain = true)
public class SysLogInfo implements Serializable {
    
    @ApiModelProperty(value = "调用者id")
    private Long operatorId;
    
    @ApiModelProperty(value = "调用者姓名")
    private String operatorName;
    
    @ApiModelProperty(value = "调用方法时的时间")
    private LocalDateTime operatingTime;
    
    @ApiModelProperty(value = "被调用方法的描述")
    private String methodDesc;
    
    @ApiModelProperty(value = "用户ip")
    private String ip;
    
    @ApiModelProperty(value = "浏览器代理")
    private String userAgent;
    
    @ApiModelProperty(value = "uri")
    private String uri;
    
    @ApiModelProperty(value = "url")
    private String url;
    
    @ApiModelProperty(value = "被调用的方法名")
    private String method;
    
    @ApiModelProperty(value = "方法耗时")
    private Long timeConsumed;
    
    @ApiModelProperty(value = "请求参数")
    private String params;
    
    @ApiModelProperty(value = "返回值")
    private String returnValue;
    
    @ApiModelProperty(value = "是否发生异常")
    private Boolean withExceptions;
    
    @ApiModelProperty(value = "发生异常时的异常信息")
    private String exceptionMsg;
}
