package org.origami.upm.api.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.origami.common.mybatis.entity.BaseEntity;

/**
 * 权限实体类
 *
 * @author origami
 * @date 2022/1/11 20:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysPermission extends BaseEntity {
    
    @ApiModelProperty(value = "父级权限id")
    private Long parentId;
    
    @ApiModelProperty(value = "权限名称", example = "添加用户")
    private String permissionName;
    
    @ApiModelProperty(value = "类型，0为菜单类型，1为按钮类型")
    private Integer type;
    
    @ApiModelProperty(value = "权限")
    private String perms;
    
    @ApiModelProperty(value = "url")
    private String url;
    
    @ApiModelProperty(value = "排序")
    private Integer sort;
    
}
