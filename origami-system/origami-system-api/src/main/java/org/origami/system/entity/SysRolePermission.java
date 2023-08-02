package org.origami.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.origami.boot.mybatisplus.entity.BaseEntity;

/**
 * 角色权限关联表
 *
 * @author origami
 * @date 2022/1/11 22:40
 */
@Data
@Accessors(chain = true)
@TableName("sys_role_permission")
@EqualsAndHashCode(callSuper = true)
public class SysRolePermission extends BaseEntity {
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "权限id")
    private Long permissionId;
}
