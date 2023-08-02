package org.origami.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.origami.boot.mybatisplus.entity.BaseEntity;

/**
 * 用户角色关联表
 *
 * @author origami
 * @date 2022/1/11 22:38
 */
@Data
@Accessors(chain = true)
@TableName("sys_user_role")
@EqualsAndHashCode(callSuper = true)
public class SysUserRoleDO extends BaseEntity {
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "角色id")
    private Long roleId;
}
