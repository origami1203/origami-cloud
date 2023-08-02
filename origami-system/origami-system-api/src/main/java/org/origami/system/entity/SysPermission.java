package org.origami.system.entity;

import java.util.Objects;

import org.origami.boot.mybatisplus.entity.BaseEntity;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 权限实体类
 *
 * @author origami
 * @date 2022/1/11 20:17
 */
@Data
@TableName("sys_permission")
public class SysPermission extends BaseEntity {

    @ApiModelProperty(value = "父级权限id")
    private Long parentId;

    @ApiModelProperty(value = "权限名称", example = "添加用户")
    private String permissionName;

    @ApiModelProperty(value = "类型，0为菜单类型，1为按钮类型")
    private Integer type;

    @ApiModelProperty(value = "权限", example = "sys:user:add")
    private String perms;

    @ApiModelProperty(value = "url", example = "/sys/user:post")
    private String url;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysPermission that = (SysPermission)o;
        return Objects.equals(id, that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
