package org.origami.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.origami.boot.mybatisplus.entity.BaseEntity;


/**
 * 角色实体类
 *
 * @author origami
 * @date 2022/1/11 20:10
 */
@Data
@TableName("sys_role")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "角色实体类")
public class SysRoleDO extends BaseEntity {

    @ApiModelProperty(value = "角色名称", example = "管理员")
    private String roleName;

    @ApiModelProperty(value = "角色标识", example = "ADMIN")
    private String roleCode;

    @ApiModelProperty(value = "备注")
    private String remark;
}
