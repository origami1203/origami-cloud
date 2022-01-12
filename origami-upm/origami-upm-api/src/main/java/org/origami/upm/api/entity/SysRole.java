package org.origami.upm.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.origami.common.mybatis.entity.BaseEntity;

import java.io.Serializable;

/**
 * 角色实体类
 *
 * @author origami
 * @date 2022/1/11 20:10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "角色实体类")
public class SysRole extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 2131384971058799092L;
    
    @ApiModelProperty(value = "角色名称", example = "管理员")
    private String roleName;
    
    @ApiModelProperty(value = "角色标识", example = "ADMIN")
    private String roleCode;
    
    @ApiModelProperty(value = "备注")
    private String remark;
}
