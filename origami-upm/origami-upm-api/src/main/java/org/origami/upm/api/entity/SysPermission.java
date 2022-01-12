package org.origami.upm.api.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.origami.common.mybatis.entity.BaseEntity;

import java.io.Serializable;

/**
 * 权限实体类
 *
 * @author origami
 * @date 2022/1/11 20:17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysPermission extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -2098005829828741836L;
    
    @ApiModelProperty(value = "父级权限id")
    private Long parentId;
    
    @ApiModelProperty(value = "权限名称", example = "添加用户")
    private String permissionName;
    
    @ApiModelProperty(value = "类型，0为菜单类型，1为url类型")
    private Integer type;
    
    @ApiModelProperty(value = "权限")
    private String perms;
    
}
