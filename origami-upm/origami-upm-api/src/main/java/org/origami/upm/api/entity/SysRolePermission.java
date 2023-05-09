package org.origami.upm.api.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.origami.common.mybatis.entity.BaseEntity;

/**
 * 角色权限关联表
 *
 * @author origami
 * @date 2022/1/11 22:40
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysRolePermission extends BaseEntity {
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "权限id")
    private Long permissionId;
}
