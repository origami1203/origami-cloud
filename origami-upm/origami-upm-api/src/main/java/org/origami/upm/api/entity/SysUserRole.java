package org.origami.upm.api.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.origami.common.mybatis.entity.BaseEntity;

/**
 * 用户角色关联表
 *
 * @author origami
 * @date 2022/1/11 22:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserRole extends BaseEntity {
    @ApiModelProperty(value = "用户id")
    private Long userId;
    
    @ApiModelProperty(value = "角色id")
    private Long roleId;
}
